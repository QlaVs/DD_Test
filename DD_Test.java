/*
(C)QlaVs
Digital Design test project
*/
//  \d\[[^\[]*\]
//  \d[^\[]|\d\[[^\[]
//  \d\[[^\[]*\]|\d\[[^\[]|\d*[\]]{2,100}
//  [a-z]\d\[[^\W].+|\d\[[^\W].+|\w -> best


import java.util.Scanner;

public class DD_Test {

    public static void main(String[] args) {
        //input
        Scanner input = new Scanner(System.in);
        System.out.print("Input: ");
        String main_string = input.nextLine();
//        String main_string = "2[z3[2[gf]]]";
        char[] main_chars = main_string.toCharArray();

        int start_pos = 0;

        /*
        first err cather
        check if brackets balanced
        */
        if (balance(main_string)) {
            System.out.println(check(main_chars, start_pos));
        } else {
            err();
        }

    }

// 3[f2[g]] = 3[fgg] = fgg*fgg*fgg -> working
// 3[2[g]f] = 3[ggf] = ggf*ggf*ggf -> working

    //main part
    static String check(char[] main_chars, int start_pos) {

        //output string
        StringBuilder output = new StringBuilder();

        for (int i = start_pos; i < main_chars.length; i++) {

            boolean number = Character.isDigit(main_chars[i]);

            if (!number && main_chars[i] != '[' && main_chars[i] != ']') {
                output.append(main_chars[i]);
                start_pos++;

            } else if (number) {
                int multiplier = Character.getNumericValue(main_chars[i]);
                StringBuilder sub_str = new StringBuilder();

                boolean repeat = false;
                //check func
                int j = 2;
                int inner_loop = 0;
                while (main_chars[i + j] != ']') {
                    if (main_chars[i + j] == '[') {
                        inner_loop++;
                    }
                    if (Character.isDigit(main_chars[i + j])) {
                        repeat = true;
                    }
                    sub_str.append(main_chars[i + j]);
                    j++;
                }

                StringBuilder temp_str = new StringBuilder();
                if (repeat) {
                    //add lost brackets
                    sub_str.append("]".repeat(Math.max(0, inner_loop)));
                    j++;
                    while (main_chars[i + j] != ']') {
                        sub_str.append(main_chars[i + j]);
                        j++;
                    }

                    /*
                    second err cather
                    check each iteration (regex using)
                     */
                    if (!sub_str.toString().matches("[a-z]*\\d\\[[^\\W].+|\\d\\[[^\\W].+")) {
                        err();
                    } else {
                        //Recursive check (each iteration goes deeper (2[g3[f]] -> g3[f] -> f)
                        temp_str.append(check(sub_str.toString().toCharArray(), 0));
                    }
                    sub_str = temp_str;
                }
                i += j;
                //add part to output string
                output.append(sub_str.toString().repeat(multiplier));
            }
        }
        return output.toString();
    }

    //quantity = 0; [ = quantity + 1; ] = quantity - 1; if quantity = 0 -> true;
    static boolean balance(String s) {
        int quantity = 0;
        for (char ch: s.toCharArray()) {
            if (ch == '[') {
                quantity++;
            } else if (ch == ']') {
                quantity--;
            }
        }
        return quantity == 0;
    }

    static void err() {
        System.out.println("Error: Incorrect input");
        System.exit(0);
    }
}
