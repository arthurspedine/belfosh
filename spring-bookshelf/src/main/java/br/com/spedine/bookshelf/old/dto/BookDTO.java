package br.com.spedine.bookshelf.old.dto;

public record BookDTO(
        Long id,
        String title,
        String publishedDate,
        String publisher,
        String summary,
        Integer totalPages,
        AuthorDTO author,
        String poster_url
) {
    @Override
    public String toString() {
        return "{" +
                "id: " + id +
                ", title: '" + title + '\'' +
                ", publishedDate: '" + publishedDate + '\'' +
                ", publisher: '" + publisher + '\'' +
                ", summary: '" + summary + '\'' +
                ", totalPages: " + totalPages +
                ", author: " + author.name() +
                ", poster_url: '" + poster_url + '\'' +
                '}';
    }
}
