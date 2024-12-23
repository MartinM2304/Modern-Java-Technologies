package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TextTokenizer {

    private final Set<String> stopwords;

    public TextTokenizer(Reader stopwordsReader) {
        if (stopwordsReader == null) {
            throw new IllegalArgumentException("stopWordsReader is null");
        }
        try (var br = new BufferedReader(stopwordsReader)) {
            stopwords = br.lines().collect(Collectors.toSet());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not load dataset", ex);
        }
    }

    public List<String> tokenize(String input) {
        if (input == null || input.isBlank() || input.isEmpty()) {
            throw new IllegalArgumentException("input is null");
        }

        String cleanedInput = input
                .replaceAll("\\p{Punct}", "")
                .replaceAll("\\s+", " ")
                .toLowerCase();
        String[] result = cleanedInput.split(" ");

        return List.of(result).stream().filter(word-> !stopwords.contains(word)).collect(Collectors.toList());
    }

    public Set<String> stopwords() {
        return stopwords;
    }

}
