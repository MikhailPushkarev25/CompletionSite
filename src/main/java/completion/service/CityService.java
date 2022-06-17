package completion.service;

import completion.model.City;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CityService {

    Map<Integer, City> cities = new ConcurrentHashMap<>();

    public CityService() {
        cities.put(1, new City(1, "Москва"));
        cities.put(2, new City(2, "Санкт-Петербург"));
        cities.put(3, new City(3, "Нижний Новгород"));
    }

    public List<City> findAllCity() {
        return new ArrayList<>(cities.values());
    }

    public City findById(int id) {
        return cities.get(id);
    }
}
