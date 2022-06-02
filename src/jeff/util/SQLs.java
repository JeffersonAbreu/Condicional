package jeff.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLs {
    private static Map<String, Map<String, String>> scripts;

    public static void start() {
        List<String> TABELA = Arrays.asList("atendente", "cliente", "condicional", "itensCondicional", "roupa");
        Map<String, Map<String, String>> script = new HashMap<String, Map<String, String>>();
        // Mapeando
        script.put("INSERT", new HashMap<String, String>());
        script.put("UPDATE", new HashMap<String, String>());
        script.put("DELETE", new HashMap<String, String>());
        script.put("SELECT", new HashMap<String, String>());
        script.put("SELECT_ALL", new HashMap<String, String>());
        script.put("SELECT_FK", new HashMap<String, String>());

        /*
         * O método "keySet()" retorna um Set com todas as chaves do
         * nosso HashMap, e tendo o Set com todas as Chaves,
         * podemos facilmente pegar
         * os valores que desejamos
         */
        String path = new File("db/scripts").getAbsolutePath().toString();
        String url = null;
        for (String comando : script.keySet()) {
            for (String tabela : TABELA) {
                try {
                    url = path + "\\" + tabela + "\\" + comando + ".sql";
                    // Le o arquivo
                    String texto = Files.readString(Path.of(url), StandardCharsets.UTF_8);
                    script.get(comando).put(tabela.toUpperCase(), texto);
                } catch (IOException e) {
                }
            }
        }
        scripts = script;
    }

    public static String DELETE(String table) {
        return scripts.get("DELETE").get(table);
    }

    public static String INSERT(String table) {
        return scripts.get("INSERT").get(table);
    }

    public static String SELECT(String table) {
        return scripts.get("SELECT").get(table);
    }

    public static String SELECT_ALL(String table) {
        return scripts.get("SELECT_ALL").get(table);
    }

    public static String SELECT_FK(String table) {
        return scripts.get("SELECT_FK").get(table);
    }

    public static String UPDATE(String table) {
        return scripts.get("UPDATE").get(table);
    }

}
