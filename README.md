# ATAD 2026/27 · Laboratório 3 — Pesquisar e ordenar

| | |
|---|---|
| **Unidade curricular** | Algoritmos e Tipos Abstratos de Dados |
| **Semana** | 4 (aplica a matéria da semana 3) |
| **Duração** | 1 h 45 min |
| **Meio** | IntelliJ IDEA, JDK 21 (ou o disponível na sala) |
| **Conteúdo de suporte** | Tópicos 9–12: pesquisa linear e binária, mínimo e máximo, Bubble Sort, Selection Sort, comparação |
| **Trabalho** | Individual ou em grupo de 2 (à escolha dos estudantes) |

---

## 1. Objetivos

No final deste laboratório deves ser capaz de:

1. Implementar a **pesquisa binária** e explicar a sua pré-condição;
2. Implementar **Bubble Sort** (com paragem antecipada) e **Selection Sort**;
3. Instrumentar os algoritmos com contadores de **comparações** e **trocas**;
4. Comparar os dois algoritmos em dados aleatórios, ordenados e invertidos;
5. Escolher o algoritmo adequado a uma situação, justificando com dados medidos.

---

## 2. O que é fornecido

`src/Ordenacao.java` contém:

| Método | Estado |
| --- | --- |
| `pesquisaLinear` | implementado |
| `indiceMinimo` | implementado |
| `pesquisaBinaria` | **a implementar** |
| `bubbleSort` | **a implementar** |
| `selectionSort` | **a implementar** |
| `copia`, `crescente`, `inverso`, `aleatorio`, `estaOrdenado` | utilitários |

Os contadores `comparacoes` e `trocas` são campos estáticos: **têm de ser repostos a zero antes de cada medição**.

**Regra:** não alteres a lógica de `pesquisaLinear` nem de `indiceMinimo`; são a referência para o estilo de contagem.

---

## 3. Plano da sessão

| Parte | Tarefa | Tempo |
| :---: | --- | :---: |
| 1 | Criar o projeto e executar o código fornecido | 10 min |
| 2 | Pesquisa: implementar a binária e medir | 25 min |
| 3 | Ordenação: implementar Bubble e Selection | 30 min |
| 4 | Comparação: medir os dois no mesmo array | 25 min |
| 5 | Conclusões | 15 min |

---

## 4. Parte 1 — Criar o projeto e testar (10 min)

1. `File` → `New` → `Project…`;
2. Escolhe `Java` no lado direito do *wizard*.
3. Configurações (segue esta ordem):
  - **Location**: (cria/escolhe uma pasta para os projetos de ATAD) 
  - **Name**: `atad-lab3`
  - **Build System**: `Maven`
  - **JDK**: Qualquer existente `>= 21`;
  - `Add sample code` desabilitado -> Sem código inicial.  
  - Advanced Settings:
    - **Group**: `pt.unips.atad`
    - **Artifact Id**: `atad-lab3`
4. Cria a classe `Ordenacao` em `src/main/java` e e coloca conteúdo de `src/Ordenacao.java` deste repositório.
5. Executa `Ordenacao.main`.

O programa deve correr sem erros. Os arrays ainda **não** estão ordenados e a pesquisa binária devolve `-1` — é o esperado, porque os métodos ainda não estão implementados.

---

## 5. Parte 2 — Pesquisa (25 min)

### 5.1. Implementar a pesquisa binária

1. **Segue o pseudo-código da aula**. Na contagem, incrementa `comparacoes` **uma vez por iteração**, antes de comparar.

2. Executa novamente o programa e repara no número de comparações necessárias para encontrar o valor `7` na pesquisa linear e binária.

O resultado esperado é:

```text
arr = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
linear(arr, 7)    = 7  (8 comparações)
binaria(arr, 7)   = 7  (2 comparações)
indiceMinimo(arr) = 2  (9 comparações)
```

### 5.2. Medir

Estando os métodos instrumentados, vamos procurar um valor **ausente e maior do que todos** (`n`), em `crescente(n)`. É o pior caso.

1. Adicione o seguinte código ao `main` e execute.
   
```java
System.out.printf("%10s %10s %10s\n", "n", "linear", "binaria");
for (int n = 100; n <= 10000; n *= 10) {
    int[] arr = crescente(n);
    int val = n; // n estará sempre fora de [0..n-1]

    comparacoes = 0;
    pesquisaLinear(arr, val);
    long linear = comparacoes;

    comparacoes = 0;
    pesquisaBinaria(arr, val);
    long binaria = comparacoes;

    System.out.printf("%10d %10d %10d\n", n, linear, binaria);
}
```

2. **Cria o ficheiro `notas.md`** e preencha a tabela com os resultados; utiliza a calculadora ou o *google* para pedir os valores de $\log_2 n$.

|    $n$ | Linear | Binária | $\log_2 n$ |
| -----: | -----: | ------: | ---------: |
|    100 |        |         |            |
|  1 000 |        |         |            |
| 10 000 |        |         |            |

<small>

Deves obter, na binária, **7**, **10** e **14** iterações — $\lfloor \log_2 n \rfloor + 1$. Se obtiveres valores muito diferentes, verifica se repões o contador e se o ciclo termina.

</small>

### 5.3. A pré-condição

Vamos criar um array **desordenado** e procurar valores que sabemos existir (presentes no próprio array).

1. Adiciona o seguinte código ao `main` e executa.

```java
int[] v = aleatorio(20);
System.out.println(Arrays.toString(v));
for (int i = 0; i < v.length; i += 2) {
    comparacoes = 0;
    int r = pesquisaBinaria(v, v[i]);
    System.out.printf("Pesquisa de %d (índice %d). Resultado pesquisa binária = %d (%d comparacoes) -- ", v[i], i, r, comparacoes);
    System.out.println(r != -1 ? "Encontrado" : "Não encontrado"); //if-else compacto
}
```

2. **Responde em `notas.md`:** encontraste valores que existem e para os quais a pesquisa devolve `-1`? Porque acontece? O algoritmo avisa que falhou?

---

## 6. Parte 3 — Ordenação (30 min)

### 6.1. Implementar

> [!IMPORTANT]
> Novamente, utilize o pseudo-código dos slides das aulas. Não se limite a copiar o código já fornecido.

1. `bubbleSort` — com **flag** de paragem antecipada;
2. `selectionSort` — note como a lógica de `indiceMinimo` é utilizada dentro do ciclo (a partir de `i`).

### 6.2. Verificar

1. A cada execução do programa o array utilizado é diferente (aleatório). Deverá verificar que os arrays ficam ordenados em diferentes execuções:

```text
bubble:      [.. elementos ordenados crescentemente ..]  ordenado? true
selection:   [.. elementos ordenados crescentemente ..]  ordenado? true
```

2. Testa, adicionalmente, ambos os algoritmos para as seguintes situações:

- array vazio e com um elemento;
- array com elementos repetidos;
- array invertido - pode usar `invertido(n)`.

> [!WARNING]
> Para ordenar o **mesmo** array com os dois algoritmos, tens de usar `copia(a)` em cada um. Se passares o array já ordenado ao segundo, estás a medir outro problema.


3. Usa `estaOrdenado` para verificar, e não apenas a inspeção visual.

---

## 7. Parte 4 — Instrumentação e comparação (25 min)

1. Instrumenta ambos os algoritmos de ordenação: conta uma comparação por cada `a[j] > a[j+1]` (Bubble) ou `a[j] < a[indiceMin]` (Selection) e **uma** troca por cada troca executada.

2. Mede, para $n = 200$, os dois algoritmos em três arrays diferentes. Repõe os contadores **antes de cada** chamada e usa sempre uma cópia.

| Array            | Bubble: comparações | Bubble: trocas | Selection: comparações | Selection: trocas |
| ---------------- | ------------------: | -------------: | ---------------------: | ----------------: |
| `aleatorio(200)` |                     |                |                        |                   |
| `crescente(200)` |                     |                |                        |                   |
| `inverso(200)`   |                     |                |                        |                   |

<small>

Valores de referência com a instrumentação indicada: o Bubble faz **cerca de 19 800** comparações no array aleatório, **199** no ordenado e **19 900** no invertido; o Selection faz sempre **19 900**. As trocas do Bubble vão de **0** a **19 900**; as do Selection são **199** (ou menos, se evitares as trocas consigo próprio).

</small>

**Responde em `notas.md`:**

1. No array **ordenado**, porque é que o Bubble faz 199 comparações e o Selection faz 19 900?
2. No array **invertido**, porque é que as trocas do Bubble chegam a 19 900 e as do Selection não passam de 199?
3. No array **aleatório**, a flag do Bubble poupa muito? Porquê?
4. Quem faz mais **escritas** em cada situação: o Bubble ou o Selection?

---

## 8. Parte 5 — Conclusões (15 min)

Escreve em `notas.md` uma tabela final:

|              | Bubble | Selection |
| ------------ | :----: | :-------: |
| Melhor caso  |        |           |
| Pior caso    |        |           |
| Adaptativo   |        |           |
| Nº de trocas |        |           |
| Estável      |        |           |

E responde:

1. Porque é que, no array ordenado, o custo dos dois algoritmos é tão diferente, se ambos são $\Theta(n^2)$?
2. Quem escolherias para ordenar 20 elementos quase ordenados? E para 1 000 000 de elementos?
3. O Selection Sort é estável? Dá um exemplo com chaves repetidas.
4. Qual é o efeito de ordenar com o Selection um array que já está ordenado? Quantas trocas **reais** acontecem?

---

## 9. Fecho

- [ ] O projeto compila sem erros;
- [ ] `estaOrdenado` devolve `true` nos cinco métodos testados;
- [ ] As tabelas das Partes 2 e 4 estão preenchidas com valores medidos;
- [ ] O `notas.md` tem as respostas e a tabela final;
- [ ] Consegues explicar a diferença entre «executar uma troca» e «alterar o array».

---

## 10. Erros comuns

| Erro | Efeito | Como detetar |
| --- | --- | --- |
| Não repor `comparacoes = 0` | valores acumulados | a primeira linha da tabela já é enorme |
| Ordenar duas vezes o mesmo array | o segundo recebe dados já ordenados | usar `copia` |
| `esq < dir` em vez de `esq <= dir` | não examina o último elemento | binária falha em arrays de 1 elemento |
| `meio` calculado fora do ciclo | ciclo infinito | o programa não termina |
| Bubble com `j < n - i` | `ArrayIndexOutOfBoundsException` | falta o `-1` |
| Selection com `j = i` | compara o elemento consigo próprio | uma comparação a mais por passo |
| Contar 3 trocas por cada troca | totais 3× maiores | comparar com os valores de referência |
| Pesquisa binária em array desordenado | `-1` para valores existentes | testar a pré-condição |

---

## 11. Referências

- Slides do bloco 3 (semana 3) — disponíveis no Moodle;
- Exercícios de aula da semana 3 — disponíveis no Moodle;
- Documentação Java: <https://docs.oracle.com/en/java/>.
