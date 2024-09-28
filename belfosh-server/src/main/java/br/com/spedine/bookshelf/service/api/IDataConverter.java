package br.com.spedine.bookshelf.service.api;

public interface IDataConverter {
    <T> T getData(String json, Class<T> tClass);
}
