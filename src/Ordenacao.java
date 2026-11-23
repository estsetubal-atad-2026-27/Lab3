import java.util.Arrays;
import java.util.Random;

/**
 * ATAD 2026/27 - Laboratório 3
 *
 * Pesquisa e ordenação.
 *
 * Métodos já implementados: pesquisaLinear, indiceMinimo e os utilitários.
 * Métodos a implementar: pesquisaBinaria, bubbleSort, selectionSort.
 *
 * Os contadores `comparacoes` e `trocas` são repostos pelo chamador.
 */
public class Ordenacao {

    // Contadores globais (repor antes de cada medição)
    static long comparacoes;
    static long trocas;

    // ---------------------------------------------------------------
    // Pesquisa (implementado)
    // ---------------------------------------------------------------

    public static int pesquisaLinear(int[] a, int x) {
        for (int i = 0; i < a.length; i++) {
            comparacoes++;
            if (a[i] == x) {
                return i;
            }
        }
        return -1;
    }

    // ---------------------------------------------------------------
    // Pesquisa binária (a implementar)
    // ---------------------------------------------------------------

    // Pré-condição: a está ordenado crescentemente
    public static int pesquisaBinaria(int[] a, int x) {
        // TODO: implementar.
        // Contar uma comparação por iteração (comparacoes++).
        return -1;
    }

    // ---------------------------------------------------------------
    // Mínimo (implementado)
    // ---------------------------------------------------------------

    // Pré-condição: a tem pelo menos um elemento
    public static int indiceMinimo(int[] a) {
        int indiceMin = 0;
        for (int i = 1; i < a.length; i++) {
            comparacoes++;
            if (a[i] < a[indiceMin]) {
                indiceMin = i;
            }
        }
        return indiceMin;
    }

    // ---------------------------------------------------------------
    // Ordenação (a implementar)
    // ---------------------------------------------------------------

    public static void bubbleSort(int[] a) {
        // TODO: implementar (com flag de paragem antecipada).
        // Contar uma comparação por cada comparação entre elementos
        // e uma troca por cada troca efetuada.
    }

    public static void selectionSort(int[] a) {
        // TODO: implementar.
        // Contar comparações e trocas como no bubbleSort.
    }

    // ---------------------------------------------------------------
    // Utilitários (não alterar)
    // ---------------------------------------------------------------

    public static int[] copia(int[] a) {
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[i];
        }
        return b;
    }

    /** Array [0, 1, ..., n-1]. */
    public static int[] crescente(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        return a;
    }

    /** Array [n-1, n-2, ..., 0]. */
    public static int[] inverso(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = n - 1 - i;
        }
        return a;
    }

    /** Array de n valores pseudo-aleatórios em [0, 1000]. */
    public static int[] aleatorio(int n) {
        return aleatorio(n, 0, 1000);
    }

    /** Array de n valores pseudo-aleatórios em [min, max].
     *  Pré-condição: min < max
     */
    public static int[] aleatorio(int n, int min, int max) {
        Random r = new Random();
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = r.nextInt(max - min + 1) + min;
        }

        return a;
    }

    public static boolean estaOrdenado(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }

    // ---------------------------------------------------------------
    // Ponto de entrada (podes alterar)
    // ---------------------------------------------------------------

    public static void main(String[] args) {
        System.out.println("--- PARTE 2----");

        int[] arr = crescente(10);
        System.out.println("arr = " + Arrays.toString(arr));

        comparacoes = 0;
        System.out.println("linear(arr, 7)    = " + pesquisaLinear(arr, 7) + "  (" + comparacoes + " comparações)");

        comparacoes = 0;
        System.out.println("binaria(arr, 7)   = " + pesquisaBinaria(arr, 7) + "  (" + comparacoes + " comparações)");

        comparacoes = 0;
        System.out.println("indiceMinimo(arr) = " + indiceMinimo(arr) + "  (" + comparacoes + " comparações)");

        // TODO
 
        System.out.println("--- PARTE 3---");

        int[] a = aleatorio(10);
        System.out.println("antes:       " + Arrays.toString(a));

        int[] b = copia(a);
        bubbleSort(b);
        System.out.println("bubble:      " + Arrays.toString(b) + "  ordenado? " + estaOrdenado(b));

        int[] c = copia(a);
        selectionSort(c);
        System.out.println("selection:   " + Arrays.toString(c) + "  ordenado? " + estaOrdenado(c));

        // TODO
    }
}
