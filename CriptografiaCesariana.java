import java.util.Objects;

public class CriptografiaCesariana {
    final int CHAVE = 3;
    
    public String criptografar(String texto) {
        pegaErro(texto);
        char [] fraseOriginal = texto.toLowerCase().toCharArray();
        char [] fraseNova = new char[fraseOriginal.length];

        for(int i : fraseOriginal) {
            if (fraseOriginal[i] >= 97 && fraseOriginal[i] < 120) {
                fraseNova[i] = (char) (fraseOriginal[i]+CHAVE);
            }else if (fraseOriginal[i] >= 120 && fraseOriginal[i] <= 122 ) {
                fraseNova[i] = (char) (fraseOriginal[i]-20-CHAVE);
            } else {
                fraseNova[i] = (char) (fraseOriginal[i]);
            }

        }
        return new String (fraseNova);
    }
    
    public String descriptografar(String texto) {
        pegaErro(texto);
        char [] fraseOriginal = texto.toLowerCase().toCharArray();
        char [] fraseNova = new char[fraseOriginal.length];

        for(int i : fraseOriginal) {
            if (fraseOriginal[i] > 99 && fraseOriginal[i] <= 122) {
                fraseNova[i] = (char) (fraseOriginal[i]-CHAVE);
            }else if (fraseOriginal[i] >= 97 && fraseOriginal[i] <= 99 ) {
                fraseNova[i] = (char) (fraseOriginal[i]+20+CHAVE);
            } else {
                fraseNova[i] = (char) (fraseOriginal[i]);
            }

        }

        return new String (fraseNova);
    }

    private void pegaErro(String text) {
        if (Objects.isNull(text)) {
            throw new NullPointerException();
        }

        if (text.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }


}
