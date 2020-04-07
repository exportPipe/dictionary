package dictionary;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class DictionaryAPI {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dictionary<String, String> dict = null;
        while (true) {
            String[] issue;
            if (sc.hasNext()) {
                issue = sc.nextLine().split(" ");

                if (issue[0].equals("create")) {
                    if (issue.length == 1) {
                        dict = new SortedArrayDictionary<>();
                    } else if (issue.length != 2) {
                        System.out.println("Ungültige Anzahl an Parametern");
                        return;
                    } else {
                        switch (issue[1]) {
                            case "HashDict":
                                dict = new HashDictionary<>();
                                break;
                            case "BinaryTreeDict":
                                dict = new BinaryTreeDictionary<>();
                                break;
                            case "SortedArrayDict":
                                dict = new SortedArrayDictionary<>();
                                break;
                            default:
                                System.out.println("create <Dictioary typ> eingeben");
                                return;
                        }
                    }
                } else if (dict == null) {
                    System.out.println("Wörterbuch muss erstellt werden bevor es benutzt werden kann");
                    System.out.println("Eingabe: create HashDict / BinaryTreeDict / SortedArrayDict");
                } else if (issue[0].equals("read")) {
                    if (issue.length == 1 || issue.length == 2) {
                        JMenuBar m = new JMenuBar();
                        JFileChooser fc = new JFileChooser();
                        int i = fc.showOpenDialog(m);
                        File file = fc.getSelectedFile();
                        LineNumberReader in;
                        try {
                            in = new LineNumberReader(new FileReader(file));
                            String line;
                            int counter = 0;
                            while ((line = in.readLine()) != null) {
                                String[] wf = line.split(" ");
                                dict.insert(wf[0], wf[1]);
                                counter++;
                                if (issue.length == 2) {
                                    try {
                                        if (counter >= Integer.parseInt(issue[1])) break;
                                    } catch (NumberFormatException e) {
                                        System.out.println("2. argument muss eine ganze Zahl sein");
                                        break;
                                    }
                                }
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println(e.getMessage() + "File not found");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (issue.length == 1 && issue[0].equals("p")) {
                    for (Dictionary.Entry<String, String> e : dict) {
                        System.out.println(e.getKey() + ": " + e.getValue());
                    }
                } else if (issue.length == 2 && issue[0].equals("s")) {
                    String s = dict.search(issue[1]);
                    if (s == null) {
                        System.out.println(issue[1] + " ist nicht im Dict vorhanden");
                    } else {
                        System.out.println(s);
                    }
                } else if (issue.length == 3 && issue[0].equals("i")) {
                    dict.insert(issue[1], issue[2]);
                } else if (issue.length == 2 && issue[0].equals("r")) {
                    if (dict.remove(issue[1]) == null) {
                        System.out.println("Eintrag war nicht vorhanden und konnte nicht gelöscht werden");
                    }
                } else if (issue.length == 1 && issue[0].equals("exit")) {
                    return;
                } else {
                    System.out.println("Falsche Eingabe");
                }
            }
        }
    }
}