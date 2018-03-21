package com.pagatodo.yaganaste.data.room_db;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.data.room_db.entities.MontoComercio;
import com.pagatodo.yaganaste.data.room_db.entities.Paises;
import com.pagatodo.yaganaste.utils.JsonManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ozunigag on 15/03/2018.
 */

public class DatabaseManager {

    public DatabaseManager() {
    }

    /* Valida si la BD ya cuenta con el catálogo de países */
    public void checkCountries() {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return App.getAppDatabase().paisesModel().isTableEmpty();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    String json = JsonManager.loadJSONFromAsset("files/paises.json");
                    Type token = new TypeToken<List<Paises>>() {
                    }.getType();
                    List<Paises> countries = new Gson().fromJson(json, token);
                    insertCountries(countries);
                }
            }
        }.execute();
    }

    /* Inserción de la lista de paises en la BD */
    private void insertCountries(final List<Paises> countries) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().paisesModel().insertPaises(countries);
                return null;
            }
        }.execute();
    }

    /* Obtener la lista de países de la BD, ordendas de manera ascendente */
    public List<Paises> getPaisesList() throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, List<Paises>>() {
            @Override
            protected List<Paises> doInBackground(Void... voids) {
                return App.getAppDatabase().paisesModel().getListPaisesOrdered();
            }
        }.execute().get();
    }

    /* Verificar si la lista de comercios de la BD se encuentra vacía */
    public boolean isComerciosEmpty() throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return App.getAppDatabase().comercioModel().isTableEmpty();
            }
        }.execute().get();
    }

    /* Limpia la tabla e inserta la lista de comercios en la BD */
    public void insertComercios(final List<Comercio> comercios) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().comercioModel().deleteAll();
                App.getAppDatabase().montoComercioModel().deleteAll();
                App.getAppDatabase().comercioModel().insertComercios(comercios);
                for (Comercio comercio : comercios) {
                    for (Double monto : comercio.getListaMontos()) {
                        MontoComercio montoComercio = new MontoComercio();
                        montoComercio.setIdComercio(comercio.getIdComercio());
                        montoComercio.setMonto(monto);
                        /* Inserta los montos respectivos de los comercios en la BD */
                        App.getAppDatabase().montoComercioModel().insertMonto(montoComercio);
                    }
                }
                return null;
            }
        }.execute();
    }

    /* Consulta la lista de comercios y sus montos en la BD dependiendo el tipo de comercio seleccionado */
    public List<Comercio> getComerciosByType(final int idTypeComercio) throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, List<Comercio>>() {
            @Override
            protected List<Comercio> doInBackground(Void... voids) {
                List<Comercio> comercios = App.getAppDatabase().comercioModel().selectByIdType(idTypeComercio);
                for (Comercio comercio : comercios) {
                    List<MontoComercio> montosComercio = App.getAppDatabase().montoComercioModel().selectMontosByIdComercio(comercio.getIdComercio());
                    List<Double> montos = new ArrayList<>();
                    for (MontoComercio montoComercio : montosComercio) {
                        montos.add(montoComercio.getMonto());
                    }
                    comercio.setListaMontos(montos);
                }
                return comercios;
            }
        }.execute().get();
    }

    /* Consulta el Comercio y sus montos asignados en la BD */
    public Comercio getComercioById(final int idComercio) throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, Comercio>() {
            @Override
            protected Comercio doInBackground(Void... voids) {
                Comercio comercio = App.getAppDatabase().comercioModel().selectById(idComercio);
                List<MontoComercio> montosComercio = App.getAppDatabase().montoComercioModel().selectMontosByIdComercio(idComercio);
                List<Double> montos = new ArrayList<>();
                for (MontoComercio montoComercio : montosComercio) {
                    montos.add(montoComercio.getMonto());
                }
                comercio.setListaMontos(montos);
                return comercio;
            }
        }.execute().get();
    }

    /* Obtiene el logo a color del comercio guardado en la BD */
    public String getUrlLogoComercio(final String nombreComercio) throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return App.getAppDatabase().comercioModel().selectUrlComercioByNombre(nombreComercio);
            }
        }.execute().get();
    }

    /* Borra todos los favoritos de la BD */
    public void deleteFavorites() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().favoritosModel().deleteAll();
                return null;
            }
        }.execute();
    }

    /* Borra un favorito por su id en la BD */
    public void deleteFavoriteById(final int idFavorite) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().favoritosModel().deleteById(idFavorite);
                return null;
            }
        }.execute();
    }

    /* Insertar lista de favoritos en la BD */
    public void insertListFavorites(final List<Favoritos> listFavoritos) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().favoritosModel().insertListFavorites(listFavoritos);
                return null;
            }
        }.execute();
    }

    /* Insertar un favorito en la BD */
    public void insertFavorite(final Favoritos favorito) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                App.getAppDatabase().favoritosModel().insertFavorite(favorito);
                return null;
            }
        }.execute();
    }

    /* Obtener lista de favoritos por tipo de comercio de la BD */
    public List<Favoritos> getListFavoritosByIdComercio(final int idTypeComercio) throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, List<Favoritos>>(){
            @Override
            protected List<Favoritos> doInBackground(Void... voids) {
                List<Favoritos> dataFavorites = new ArrayList<>();
                List<Favoritos> favorites = App.getAppDatabase().favoritosModel().selectFavoritesByTypeComercio(idTypeComercio);
                for (Favoritos dataFav : favorites) {
                    List<MontoComercio> montosComercio = App.getAppDatabase().montoComercioModel().selectMontosByIdComercio(dataFav.getIdComercio());
                    List<Double> montos = new ArrayList<>();
                    for (MontoComercio montoComercio : montosComercio) {
                        montos.add(montoComercio.getMonto());
                    }
                    dataFav.setListaMontos(montos);
                    dataFavorites.add(dataFav);
                }
                return dataFavorites;
            }
        }.execute().get();
    }

    /* Validar si ya existe el favorito en la BD */
    public boolean favoriteAllreadyExists(final String referencia, final int idComercio) throws ExecutionException, InterruptedException {
        return new AsyncTask<Void, Void, Boolean>(){

            @Override
            protected Boolean doInBackground(Void... voids) {
                return App.getAppDatabase().favoritosModel().favoriteExists(referencia, idComercio);
            }
        }.execute().get();
    }
}
