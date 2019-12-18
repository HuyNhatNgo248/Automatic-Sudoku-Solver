//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.Random;

public class SudokuP {
    private static int[][] a = new int[9][9];

    public SudokuP() {
    }

    public static char[][] puzzle() {
        int var0 = 1;
        generate();
        randomGen(1);
        randomGen(0);
        Random var3 = new Random();
        int[] var4 = new int[]{0, 3, 6};

        for (int var5 = 0; var5 < 2; ++var5) {
            int var1 = var4[var3.nextInt(var4.length)];

            int var2;
            do {
                var2 = var4[var3.nextInt(var4.length)];
            } while (var1 == var2);

            if (var0 == 1) {
                rowChange(var1, var2);
            } else {
                colChange(var1, var2);
            }

            ++var0;
        }

        drillHoles();
        char[][] var8 = new char[9][9];

        for (int var6 = 0; var6 < 9; ++var6) {
            for (int var7 = 0; var7 < 9; ++var7) {
                if (a[var6][var7] == 0) {
                    var8[var6][var7] = '.';
                } else {
                    var8[var6][var7] = Integer.toString(a[var6][var7]).charAt(0);
                }
            }
        }

        return var8;
    }

    private static void generate() {
        boolean var0 = true;
        int var1 = 1;

        for (int var2 = 0; var2 < 9; ++var2) {
            int var4 = var1;

            for (int var3 = 0; var3 < 9; ++var3) {
                if (var4 <= 9) {
                    a[var2][var3] = var4++;
                } else {
                    byte var5 = 1;
                    a[var2][var3] = var5;
                    var4 = var5 + 1;
                }
            }

            var1 = var4 + 3;
            if (var4 == 10) {
                var1 = 4;
            }

            if (var1 > 9) {
                var1 = var1 % 9 + 1;
            }
        }

    }

    private static void randomGen(int var0) {
        int var3 = 2;
        int var4 = 0;
        Random var5 = new Random();

        for (int var6 = 0; var6 < 3; ++var6) {
            int var1 = var5.nextInt(var3 - var4 + 1) + var4;

            int var2;
            do {
                var2 = var5.nextInt(var3 - var4 + 1) + var4;
            } while (var1 == var2);

            var3 += 3;
            var4 += 3;
            if (var0 == 1) {
                permutationRow(var1, var2);
            } else if (var0 == 0) {
                permutationCol(var1, var2);
            }
        }

    }

    private static void permutationRow(int var0, int var1) {
        for (int var3 = 0; var3 < 9; ++var3) {
            int var2 = a[var0][var3];
            a[var0][var3] = a[var1][var3];
            a[var1][var3] = var2;
        }

    }

    private static void permutationCol(int var0, int var1) {
        for (int var3 = 0; var3 < 9; ++var3) {
            int var2 = a[var3][var0];
            a[var3][var0] = a[var3][var1];
            a[var3][var1] = var2;
        }

    }

    private static void rowChange(int var0, int var1) {
        for (int var3 = 1; var3 <= 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                int var2 = a[var0][var4];
                a[var0][var4] = a[var1][var4];
                a[var1][var4] = var2;
            }

            ++var0;
            ++var1;
        }

    }

    private static void colChange(int var0, int var1) {
        for (int var3 = 1; var3 <= 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                int var2 = a[var4][var0];
                a[var4][var0] = a[var4][var1];
                a[var4][var1] = var2;
            }

            ++var0;
            ++var1;
        }

    }

    private static void drillHoles() {
        Random var2 = new Random();

        for (int var3 = 0; var3 < 9; ++var3) {
            int var0 = var2.nextInt(6) + 1;

            for (int var4 = 0; var4 < var0; ++var4) {
                int var1 = var2.nextInt(9);
                a[var3][var1] = 0;
            }
        }

    }

    public static void print() {
        for (int var0 = 0; var0 < 9; ++var0) {
            for (int var1 = 0; var1 < 9; ++var1) {
                System.out.print(a[var0][var1] + ", ");
            }

            System.out.println();
        }

    }
}

