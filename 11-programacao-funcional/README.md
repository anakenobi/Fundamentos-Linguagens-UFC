##  Sistema de Biblioteca

## 📚 Estrutura dos Dados

```python
from functools import reduce, partial, lru_cache
from typing import List, Dict, Callable

# Dados simples da biblioteca
livros = [
    {"id": 1, "titulo": "Python para Iniciantes", "autor": "João", "ano": 2020, "paginas": 300, "categoria": "programacao"},
    {"id": 2, "titulo": "História do Brasil", "autor": "Maria", "ano": 2019, "paginas": 450, "categoria": "historia"},
    {"id": 3, "titulo": "JavaScript Avançado", "autor": "João", "ano": 2021, "paginas": 380, "categoria": "programacao"},
    {"id": 4, "titulo": "Culinária Italiana", "autor": "Ana", "ano": 2018, "paginas": 200, "categoria": "culinaria"},
    {"id": 5, "titulo": "Guerra Mundial", "autor": "Pedro", "ano": 2020, "paginas": 520, "categoria": "historia"},
]

emprestimos = [
    {"livro_id": 1, "usuario": "Carlos", "dias": 7},
    {"livro_id": 2, "usuario": "Lucia", "dias": 14},
    {"livro_id": 1, "usuario": "Ana", "dias": 5},
    {"livro_id": 3, "usuario": "Carlos", "dias": 10},
    {"livro_id": 4, "usuario": "João", "dias": 3},
]
```

## 🔄 1. Recursão com Cache

```python
@lru_cache(maxsize=128)
def calcular_popularidade(livro_id: int) -> int:
    """
    Recursivamente calcula popularidade baseada em empréstimos
    """
    # Encontra empréstimos deste livro
    emprestimos_livro = [e for e in emprestimos if e["livro_id"] == livro_id]
    
    if not emprestimos_livro:
        return 0  # Caso base: sem empréstimos
    
    # Recursão: cada empréstimo adiciona pontos baseados na duração
    pontos_base = len(emprestimos_livro) * 10
    
    # Bônus recursivo: quanto mais tempo emprestado, mais popular
    bonus = sum(calcular_bonus_tempo(emp["dias"]) for emp in emprestimos_livro)
    
    return pontos_base + bonus

def calcular_bonus_tempo(dias: int) -> int:
    """Função recursiva para calcular bônus por tempo"""
    if dias <= 0:
        return 0  # Caso base
    
    if dias <= 3:
        return 1
    elif dias <= 7:
        return 2 + calcular_bonus_tempo(dias - 3)  # Recursão
    else:
        return 5 + calcular_bonus_tempo(dias - 7)  # Recursão

# Exemplo de uso
print("📊 POPULARIDADE DOS LIVROS:")
for livro in livros:
    pop = calcular_popularidade(livro["id"])
    print(f"   {livro['titulo']}: {pop} pontos")
```

## 🏗️ 2. Funções de Alta Ordem

```python
def filtrar_e_mapear(dados: List[Dict], 
                     filtro: Callable, 
                     mapeador: Callable) -> List:
    """
    Função de alta ordem: recebe outras funções como parâmetros
    """
    # Primeiro filtra, depois mapeia
    dados_filtrados = filter(filtro, dados)
    dados_mapeados = map(mapeador, dados_filtrados)
    return list(dados_mapeados)

def agrupar_por(dados: List[Dict], chave: str) -> Dict:
    """
    Função de alta ordem usando reduce
    """
    return reduce(
        lambda grupos, item: {
            **grupos,
            item[chave]: grupos.get(item[chave], []) + [item]
        },
        dados,
        {}
    )

# Exemplos de uso
print("\n📚 LIVROS DE PROGRAMAÇÃO (>350 páginas):")
livros_prog = filtrar_e_mapear(
    livros,
    filtro=lambda l: l["categoria"] == "programacao" and l["paginas"] > 350,
    mapeador=lambda l: f"{l['titulo']} ({l['paginas']} páginas)"
)
for livro in livros_prog:
    print(f"   • {livro}")

print("\n👥 LIVROS POR CATEGORIA:")
por_categoria = agrupar_por(livros, "categoria")
for categoria, lista_livros in por_categoria.items():
    print(f"   {categoria.title()}: {len(lista_livros)} livros")
```

## ⚙️ 3. functools - partial e curry

```python
from functools import partial

def buscar_livros(categoria: str = None, 
                  ano_minimo: int = None, 
                  paginas_minimo: int = None) -> List[Dict]:
    """Função genérica de busca"""
    resultado = livros.copy()
    
    if categoria:
        resultado = [l for l in resultado if l["categoria"] == categoria]
    
    if ano_minimo:
        resultado = [l for l in resultado if l["ano"] >= ano_minimo]
    
    if paginas_minimo:
        resultado = [l for l in resultado if l["paginas"] >= paginas_minimo]
    
    return resultado

# Criando funções especializadas com partial
buscar_programacao = partial(buscar_livros, categoria="programacao")
buscar_recentes = partial(buscar_livros, ano_minimo=2020)
buscar_longos = partial(buscar_livros, paginas_minimo=400)

# Exemplo de uso
print("\n💻 LIVROS DE PROGRAMAÇÃO:")
for livro in buscar_programacao():
    print(f"   • {livro['titulo']} ({livro['ano']})")

print("\n📅 LIVROS RECENTES (2020+):")
for livro in buscar_recentes():
    print(f"   • {livro['titulo']} - {livro['ano']}")
```

## 🔗 4. Composição de Funções

```python
def compor(*funcoes):
    """Compõe múltiplas funções (direita para esquerda)"""
    return lambda x: reduce(lambda resultado, f: f(resultado), reversed(funcoes), x)

# Funções simples
def extrair_titulos(livros):
    return [l["titulo"] for l in livros]

def ordenar_alfabetico(lista):
    return sorted(lista)

def pegar_primeiros_3(lista):
    return lista[:3]

# Composição: pega livros → extrai títulos → ordena → pega 3 primeiros
pipeline = compor(
    pegar_primeiros_3,
    ordenar_alfabetico,
    extrair_titulos
)

print("\n🏆 TOP 3 LIVROS (ordem alfabética):")
top_3 = pipeline(livros)
for i, titulo in enumerate(top_3, 1):
    print(f"   {i}. {titulo}")
```

## 📊 5. Exemplo Completo - Relatório da Biblioteca

```python
def gerar_relatorio_biblioteca():
    """
    Função principal que usa todos os conceitos funcionais
    """
    print("📋 RELATÓRIO COMPLETO DA BIBLIOTECA")
    print("=" * 40)
    
    # 1. Análise por categoria usando funções de alta ordem
    por_categoria = agrupar_por(livros, "categoria")
    
    print("📚 ESTATÍSTICAS POR CATEGORIA:")
    for categoria, lista in por_categoria.items():
        total_paginas = sum(l["paginas"] for l in lista)
        media_paginas = total_paginas / len(lista)
        print(f"   {categoria.title()}:")
        print(f"     • Livros: {len(lista)}")
        print(f"     • Páginas médias: {media_paginas:.0f}")
    
    # 2. Top livros por popularidade (recursão + cache)
    print("\n🌟 RANKING DE POPULARIDADE:")
    livros_com_pop = [(l, calcular_popularidade(l["id"])) for l in livros]
    livros_ordenados = sorted(livros_com_pop, key=lambda x: x[1], reverse=True)
    
    for i, (livro, pop) in enumerate(livros_ordenados[:3], 1):
        print(f"   {i}. {livro['titulo']} - {pop} pontos")
    
    # 3. Análise de empréstimos usando reduce
    usuarios_ativos = reduce(
        lambda acc, emp: acc | {emp["usuario"]},
        emprestimos,
        set()
    )
    
    print(f"\n👥 USUÁRIOS ATIVOS: {len(usuarios_ativos)}")
    for usuario in sorted(usuarios_ativos):
        total_dias = sum(e["dias"] for e in emprestimos if e["usuario"] == usuario)
        print(f"   • {usuario}: {total_dias} dias total")
    
    # 4. Composição: livros longos e recentes
    livros_especiais = compor(
        lambda livros: [f"{l['titulo']} ({l['paginas']}p)" for l in livros],
        buscar_recentes,
        buscar_longos
    )(livros)
    
    print(f"\n📖 LIVROS LONGOS E RECENTES: {len(livros_especiais)}")
    for livro in livros_especiais:
        print(f"   • {livro}")

# Executar relatório
gerar_relatorio_biblioteca()
```



