# Problema Lógico - Árvore Genealógica

O sistema modela uma família através de fatos básicos (pai/mãe) e deriva outros relacionamentos usando regras lógicas.

## Estrutura da Família

```
João ♥ Lúcia
    |
    ├── Maria ♥ [parceiro]
    │      ├── Ana
    │      └── Carlos
    │
    └── Pedro ♥ Maria
           ├── Ana
           └── Carlos
```

## Código Prolog

### Fatos Básicos
```prolog
% Relacionamentos diretos pai-filho
pai(joao, maria).
pai(joao, pedro).
pai(pedro, ana).
pai(pedro, carlos).

% Relacionamentos diretos mãe-filho
mae(lucia, maria).
mae(lucia, pedro).
mae(maria, ana).
mae(maria, carlos).
```

### Regras de Inferência

```prolog
% Alguém é filho/filha se tem pai ou mãe
filho(X, Y) :- pai(Y, X).
filho(X, Y) :- mae(Y, X).

% Alguém é avô se é pai do pai ou da mãe
avo(X, Z) :- pai(X, Y), pai(Y, Z).
avo(X, Z) :- pai(X, Y), mae(Y, Z).

% Alguém é avó se é mãe do pai ou da mãe
avo_fem(X, Z) :- mae(X, Y), pai(Y, Z).
avo_fem(X, Z) :- mae(X, Y), mae(Y, Z).

% Dois indivíduos são irmãos se têm o mesmo pai ou a mesma mãe
% e são pessoas diferentes
irmao(X, Y) :- pai(Z, X), pai(Z, Y), X \= Y.
irmao(X, Y) :- mae(Z, X), mae(Z, Y), X \= Y.

% Tio/tia é irmão/irmã do pai ou da mãe
tio(X, Z) :- irmao(X, Y), pai(Y, Z).
tio(X, Z) :- irmao(X, Y), mae(Y, Z).
```

## Consultas de Exemplo

```prolog
?- pai(joao, X).           % Quem são os filhos de João?
?- avo(joao, X).           % Quem são os netos de João?
?- irmao(maria, X).        % Quem são os irmãos de Maria?
?- tio(pedro, X).          % Pedro é tio de quem?
?- filho(ana, X).          % Quem são os pais de Ana?
```

## Desafios para Resolver

Com base nos fatos e regras apresentados, determine:

1. **Quantos netos João tem?**
2. **Ana e Carlos são irmãos?**
3. **Pedro é tio de alguém?**
4. **Quem são todos os avós na família?**

## Respostas Esperadas

<details>
<summary>Clique para ver as respostas</summary>

1. **João tem 2 netos**: Ana e Carlos
   - `?- avo(joao, X).` retorna `X = ana; X = carlos`

2. **Sim, Ana e Carlos são irmãos**
   - Ambos têm Pedro como pai e Maria como mãe
   - `?- irmao(ana, carlos).` retorna `true`

3. **Não, Pedro não é tio de ninguém neste modelo**
   - Para ser tio, precisaria ter sobrinhos (filhos de seus irmãos)
   - Maria não tem filhos além dos que Pedro também tem

4. **Os avós são**: João e Lúcia
   - `?- avo(X, _).` retorna `X = joao`
   - `?- avo_fem(X, _).` retorna `X = lucia`

</details>


