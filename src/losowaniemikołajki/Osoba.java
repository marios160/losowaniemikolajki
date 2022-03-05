
package losowaniemikołajki;

/**
 *
 * @author Eduardo
 */
public class Osoba {
    private String nazwa;
    private String email;
    private boolean malzenstwo;
    private Osoba malzonek;
    private Osoba wylosowana1;
    private Osoba wylosowana2;


    public Osoba(String nazwa, String email, boolean malzenstwo) {
        this.nazwa = nazwa;
        this.email = email;
        this.malzenstwo = malzenstwo;
    }
    
    public String toString(){
        return this.nazwa + " : " + this.email + " : " + this.malzenstwo;
    }

   
    
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMalzenstwo() {
        return malzenstwo;
    }

    public void setMalzenstwo(boolean malzenstwo) {
        this.malzenstwo = malzenstwo;
    }

    public Osoba getMalzonek() {
        return malzonek;
    }

    public void setMalzonek(Osoba malzonek) {
        this.malzonek = malzonek;
    }

    public Osoba getWylosowana1() {
        return wylosowana1;
    }

    public void setWylosowana1(Osoba wylosowana1) {
        this.wylosowana1 = wylosowana1;
    }

    public Osoba getWylosowana2() {
        return wylosowana2;
    }

    public void setWylosowana2(Osoba wylosowana2) {
        this.wylosowana2 = wylosowana2;
    }
    
    
    
}
