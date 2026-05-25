public class utazok {
    public String nev;
    public String varos;
    public int honap;
    public int nap;
    public int ora;
    public int perc;

    public utazok(String sor) {
        String tomb[] = sor.split(";");
        nev = tomb[0];
        varos = tomb[1];
        honap = Integer.parseInt(tomb[2].split("\\.")[0]);
        nap = Integer.parseInt(tomb[2].split("\\.")[1]);
        ora = Integer.parseInt(tomb[3].split(":")[0]);
        perc = Integer.parseInt(tomb[3].split(":")[1]);

    }

    public String getNev() {
        return nev;
    }

    public String getVaros() {
        return varos;
    }

    public int getHonap() {
        return honap;
    }

    public int getNap() {
        return nap;
    }

    public int getOra() {
        return ora;
    }

    public int getPerc() {
        return perc;
    }
}
