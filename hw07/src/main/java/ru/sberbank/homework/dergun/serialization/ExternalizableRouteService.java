package ru.sberbank.homework.dergun.serialization;

import ru.sberbank.homework.common.CachePathProvider;
import ru.sberbank.homework.common.City;
import ru.sberbank.homework.common.Route;
import ru.sberbank.homework.common.RouteService;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ExternalizableRouteService extends RouteService<City, Route<City>> {
    private Set<String> statistic = new HashSet<>();

    public ExternalizableRouteService(CachePathProvider cachePathProvider) {
        super(cachePathProvider, false);
    }

    @Override
    public Route<City> getRoute(String from, String to) {
        Route<City> route = null;
        String key = from + "_" + to;
        if (!statistic.contains(key)) {
            route = super.getRoute(from, to);
            statistic.add(key);
            try (FileOutputStream fos = new FileOutputStream(pathProvider.getCacheDirectoryPath() + "\\" + key + ".txt");
                 ObjectOutputStream out = new ObjectOutputStream(fos);) {
                ((Externalizable) route).writeExternal(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileInputStream fis = new FileInputStream(pathProvider.getCacheDirectoryPath() + "\\" + key + ".txt");
                 ObjectInputStream in = new ObjectInputStream(fis);) {
                ExternalizableRoute ext = new ExternalizableRoute();
                ext.readExternal(in);
                route = ext;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return route;
    }

    @Override
    protected City createCity(int id, String cityName, LocalDate foundDate, long numberOfInhabitants) {
        return new ExternalizableCity(id, cityName, foundDate, numberOfInhabitants);
    }

    @Override
    protected Route<City> createRoute(List<City> cities) {
        return new ExternalizableRoute(UUID.randomUUID().toString(), cities);
    }
}