package challenge;

import java.util.Objects;

public class CriptografiaCesariana implements Criptografia {
    final int CHAVE = 3;

    @Override
    public String criptografar(String texto) {
        pegaErro(texto);
        char [] fraseOriginal = texto.toLowerCase().toCharArray();
        char [] fraseNova = new char[fraseOriginal.length];

       for(int i = 0; i<fraseNova.length; i++) {
            fraseNova[i] = (char) (fraseOriginal[i]);
            if (fraseOriginal[i] >= 97 && fraseOriginal[i] < 120) { fraseNova[i] = (char) (fraseOriginal[i]+CHAVE); }
            if (fraseOriginal[i] >= 120 && fraseOriginal[i] <= 122 ) { fraseNova[i] = (char) (fraseOriginal[i]-20-CHAVE); }
        }
        return new String (fraseNova);
    }

    @Override
    public String descriptografar(String texto) {
        pegaErro(texto);
        char [] fraseOriginal = texto.toLowerCase().toCharArray();
        char [] fraseNova = new char[fraseOriginal.length];

        for(int i = 0; i<fraseNova.length; i++) {
            fraseNova[i] = (char) (fraseOriginal[i]);
            if (fraseOriginal[i] > 99 && fraseOriginal[i] <= 122) { fraseNova[i] = (char) (fraseOriginal[i]-CHAVE); }
            if (fraseOriginal[i] >= 97 && fraseOriginal[i] <= 99 ) {fraseNova[i] = (char) (fraseOriginal[i]+20+CHAVE); }
        }
        return new String (fraseNova);
    }

    private void pegaErro(String text) {
        if (Objects.isNull(text)) throw new NullPointerException();
        if (text.isEmpty()) throw new IllegalArgumentException();
    }

}
