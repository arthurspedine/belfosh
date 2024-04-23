package br.com.spedine.bookshelf.service;

public interface IDataConverter {
    <T> T getData(String json, Class<T> tClass);
}
