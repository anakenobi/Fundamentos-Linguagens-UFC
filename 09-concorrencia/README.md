# Threads vs Processos

**Thread**: Várias tarefas dentro do mesmo programa compartilhando memória  
**Processo**: Programas separados, cada um com sua própria memória

## 📊 Diferenças Básicas

| | Threads | Processos |
|---|---|---|
| **Memória** | Compartilhada | Separada |
| **Velocidade** | Rápido para criar | Mais lento |
| **Comunicação** | Fácil (variáveis) | Mais complexa |
| **Falhas** | Uma quebra, todas quebram | Isoladas |

## 🐍 Regra Simples no Python

- **Threading**: Para esperar (arquivos, internet, input do usuário)
- **Multiprocessing**: Para calcular (matemática pesada, processamento)

## 💻 Exemplos Práticos

### 1. Threading - Fazendo Downloads

```python
import threading
import time

def baixar_arquivo(nome_arquivo):
    print(f"📥 Começando download: {nome_arquivo}")
    time.sleep(2)  # Simula tempo de download
    print(f"✅ Terminou download: {nome_arquivo}")

# SEM threading (sequencial)
print("🐌 SEM THREADING:")
start = time.time()
baixar_arquivo("video.mp4")
baixar_arquivo("musica.mp3")
baixar_arquivo("foto.jpg")
tempo_sem = time.time() - start
print(f"Tempo total: {tempo_sem:.1f}s\n")

# COM threading (paralelo)
print("🚀 COM THREADING:")
start = time.time()

# Cria as threads
thread1 = threading.Thread(target=baixar_arquivo, args=("video.mp4",))
thread2 = threading.Thread(target=baixar_arquivo, args=("musica.mp3",))
thread3 = threading.Thread(target=baixar_arquivo, args=("foto.jpg",))

# Inicia todas
thread1.start()
thread2.start()
thread3.start()

# Espera todas terminarem
thread1.join()
thread2.join()
thread3.join()

tempo_com = time.time() - start
print(f"Tempo total: {tempo_com:.1f}s")
print(f"Speedup: {tempo_sem/tempo_com:.1f}x mais rápido!")
```

**Saída esperada:**
```
🐌 SEM THREADING:
📥 Começando download: video.mp4
✅ Terminou download: video.mp4
📥 Começando download: musica.mp3
✅ Terminou download: musica.mp3
📥 Começando download: foto.jpg
✅ Terminou download: foto.jpg
Tempo total: 6.0s

🚀 COM THREADING:
📥 Começando download: video.mp4
📥 Começando download: musica.mp3
📥 Começando download: foto.jpg
✅ Terminou download: video.mp4
✅ Terminou download: musica.mp3
✅ Terminou download: foto.jpg
Tempo total: 2.0s
Speedup: 3.0x mais rápido!
```

### 2. Multiprocessing - Fazendo Cálculos

```python
import multiprocessing as mp
import time

def calcular_quadrados(numeros):
    """Calcula quadrado de uma lista de números"""
    resultado = []
    for num in numeros:
        # Simula cálculo pesado
        quadrado = num ** 2
        resultado.append(quadrado)
        time.sleep(0.1)  # Simula processamento
    
    print(f"🔢 Processo calculou: {len(numeros)} números")
    return resultado

# Números para processar
todos_numeros = list(range(1, 21))  # 1 a 20

# SEM multiprocessing
print("🐌 SEM MULTIPROCESSING:")
start = time.time()
resultado_sem = calcular_quadrados(todos_numeros)
tempo_sem = time.time() - start
print(f"Resultado: {resultado_sem[:5]}... (primeiros 5)")
print(f"Tempo: {tempo_sem:.1f}s\n")

# COM multiprocessing
print("🚀 COM MULTIPROCESSING:")
start = time.time()

# Divide em 4 partes
parte1 = todos_numeros[0:5]    # 1-5
parte2 = todos_numeros[5:10]   # 6-10
parte3 = todos_numeros[10:15]  # 11-15
parte4 = todos_numeros[15:20]  # 16-20

# Cria processes
with mp.Pool(processes=4) as pool:
    # Executa em paralelo
    resultados = pool.map(calcular_quadrados, [parte1, parte2, parte3, parte4])

# Junta todos os resultados
resultado_com = []
for lista in resultados:
    resultado_com.extend(lista)

tempo_com = time.time() - start
print(f"Resultado: {resultado_com[:5]}... (primeiros 5)")
print(f"Tempo: {tempo_com:.1f}s")
print(f"Speedup: {tempo_sem/tempo_com:.1f}x mais rápido!")
```

**Saída esperada:**
```
🐌 SEM MULTIPROCESSING:
🔢 Processo calculou: 20 números
Resultado: [1, 4, 9, 16, 25]... (primeiros 5)
Tempo: 2.0s

🚀 COM MULTIPROCESSING:
🔢 Processo calculou: 5 números
🔢 Processo calculou: 5 números
🔢 Processo calculou: 5 números
🔢 Processo calculou: 5 números
Resultado: [1, 4, 9, 16, 25]... (primeiros 5)
Tempo: 0.6s
Speedup: 3.3x mais rápido!
```

### 3. Exemplo Prático - Contador Compartilhado

```python
import threading
import multiprocessing as mp
import time

# PROBLEMA: Threads compartilham variáveis (pode dar problema!)
contador_global = 0
lock = threading.Lock()

def incrementar_thread():
    global contador_global
    for _ in range(100000):
        with lock:  # Usa lock para evitar problemas
            contador_global += 1

def incrementar_processo(contador_compartilhado):
    for _ in range(100000):
        with contador_compartilhado.get_lock():
            contador_compartilhado.value += 1

# Teste com Threads
print("🧵 TESTE COM THREADS:")
contador_global = 0
start = time.time()

threads = []
for i in range(2):
    t = threading.Thread(target=incrementar_thread)
    threads.append(t)
    t.start()

for t in threads:
    t.join()

print(f"Contador final: {contador_global}")
print(f"Esperado: 200000")
print(f"Tempo: {time.time() - start:.2f}s\n")

# Teste com Processos
print("🔄 TESTE COM PROCESSOS:")
start = time.time()

# Contador compartilhado entre processos
contador_compartilhado = mp.Value('i', 0)

processos = []
for i in range(2):
    p = mp.Process(target=incrementar_processo, args=(contador_compartilhado,))
    processos.append(p)
    p.start()

for p in processos:
    p.join()

print(f"Contador final: {contador_compartilhado.value}")
print(f"Esperado: 200000")
print(f"Tempo: {time.time() - start:.2f}s")
```

## 🎯 Resumo Simples

### Use Threading quando:
- ✅ Baixando arquivos da internet
- ✅ Lendo/escrevendo arquivos
- ✅ Esperando input do usuário
- ✅ Fazendo requisições HTTP

### Use Multiprocessing quando:
- ✅ Fazendo cálculos matemáticos
- ✅ Processando imagens/vídeos
- ✅ Análise de dados grandes
- ✅ Algoritmos que usam muito CPU




