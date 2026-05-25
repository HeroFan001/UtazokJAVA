import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {

    void main() {
        // 0) Beolvasás
        List<utazok> Utazok = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("utazok.csv"), "UTF-8")) {
            while (scanner.hasNextLine()) {
                String sor = scanner.nextLine();
                if (!sor.isBlank()) Utazok.add(new utazok(sor));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("0) Beolvasott adatok száma: " + Utazok.size());

        // 1) Városok
        Set<String> varosSet = new HashSet<>();
        for (utazok u : Utazok) varosSet.add(u.varos);
        System.out.println("\n1) Különböző városok száma: " + varosSet.size());

        List<String> varosLista = new ArrayList<>(varosSet);
        Random rnd = new Random();
        String valasztott = varosLista.get(rnd.nextInt(varosLista.size()));
        int varosDb = 0;
        for (utazok u : Utazok) if (u.varos.equals(valasztott)) varosDb++;
        System.out.println("   Véletlenszerűen választott: " + valasztott);
        System.out.println("   Oda utazók száma: " + varosDb);

        // 2) Legkorábbi indulás + délelőtt
        utazok legkorabbi = Utazok.get(0);
        int delElott = 0;
        for (utazok u : Utazok) {
            if (u.ora * 60 + u.perc < legkorabbi.ora * 60 + legkorabbi.perc) {
                legkorabbi = u;
            }
            if (u.ora < 12) delElott++;
        }
        System.out.println("\n2) Legkorábbi indulás: "
                + String.format("%02d:%02d", legkorabbi.ora, legkorabbi.perc)
                + " (" + legkorabbi.nev + ")");
        System.out.println("   Délelőtti indulások száma: " + delElott);

        // 3) Havi statisztika
        System.out.println("\n3) Havi statisztika:");
        Map<Integer, Integer> haviStat = new TreeMap<>();
        for (utazok u : Utazok) {
            haviStat.put(u.honap, haviStat.getOrDefault(u.honap, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> e : haviStat.entrySet()) {
            System.out.printf("   %02d. hónap: %d utazás%n", e.getKey(), e.getValue());
        }

        // 4) Egynél többször előforduló vezetéknevek ABC sorrendben
        System.out.println("\n4) Egynél többször előforduló vezetéknevek:");
        Map<String, Integer> nevStat = new HashMap<>();
        for (utazok u : Utazok) {
            String vezeteknev = u.nev.split(" ")[0];
            nevStat.put(vezeteknev, nevStat.getOrDefault(vezeteknev, 0) + 1);
        }
        List<String> tobbesNevek = new ArrayList<>();
        for (Map.Entry<String, Integer> e : nevStat.entrySet()) {
            if (e.getValue() > 1) tobbesNevek.add(e.getKey());
        }
        Collections.sort(tobbesNevek);
        for (String nev : tobbesNevek) System.out.println("   " + nev);

        // 5) Napok ahol kettőnél többen utaztak
        System.out.println("\n5) Napok, ahol kettőnél többen utaztak:");
        Map<String, Integer> napStat = new TreeMap<>();
        for (utazok u : Utazok) {
            String kulcs = String.format("%02d.%02d", u.honap, u.nap);
            napStat.put(kulcs, napStat.getOrDefault(kulcs, 0) + 1);
        }
        for (Map.Entry<String, Integer> e : napStat.entrySet()) {
            if (e.getValue() > 2) System.out.println("   " + e.getKey() + " - " + e.getValue() + " utazó");
        }

        // 6) szeged.txt
        List<utazok> szegediek = new ArrayList<>();
        for (utazok u : Utazok) {
            if (u.varos.equalsIgnoreCase("Szeged")) szegediek.add(u);
        }
        szegediek.sort(Comparator.comparingInt((utazok u) -> u.honap * 10000 + u.nap * 100 + u.ora * 60 + u.perc));

        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream("szeged.txt"), "UTF-8"))) {
            pw.println("Szegedre utazók:");
            pw.println("================");
            for (utazok u : szegediek) {
                pw.printf("%-25s %02d.%02d. %02d:%02d%n", u.nev, u.honap, u.nap, u.ora, u.perc);
            }
            pw.println("----------------");
            pw.println("Összesen: " + szegediek.size() + " fő");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\n6) szeged.txt elkészült (" + szegediek.size() + " rekord)");
    }
}