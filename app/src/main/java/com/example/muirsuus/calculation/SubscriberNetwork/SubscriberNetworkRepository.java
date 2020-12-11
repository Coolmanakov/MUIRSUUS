package com.example.muirsuus.calculation.SubscriberNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static java.util.Collections.emptyList;

public class SubscriberNetworkRepository {

    public enum SpinnerList {
        DIVISION_LIST,
        DEVICE_LIST,
        DEVICE_ROOM_LIST
    }

    public enum SpinnerMap {
        CONTROL_POINT_MAP,
        OFFICIAL_MAP
    }

    private final static ArrayList<String> divisionList = new ArrayList<>(Arrays.asList(
            "Полк",
            "Армия",
            "Бригада",
            "Батальон"
    ));

    private final static ArrayList<String> deviceList = new ArrayList<>(Arrays.asList(
            "ТА-57",
            "ТА-88",
            "П-380 ТА",
            "Селенит",
            "П-170",
            "П-171Д",
            "АТ-3031",
            "Рамек-2"
    ));

    private final static ArrayList<String> deviceRoomList = new ArrayList<>(Arrays.asList(
            "П-240И-4",
            "МП-1ИМ",
            "МП-2ИМ",
            "П-230Т",
            "П-260-О",
            "П-260-У",
            "П-260-Т",
            "П-244-И4",
            "П-243Т",
            "83т588",
            "П-244-И5"
    ));

    private final static HashMap<String, ArrayList<String>> controlPointMap =
            new HashMap<String, ArrayList<String>>() {{
                put("Полк", new ArrayList<>(Collections.singletonList(
                        "Командный пункт"
                )));
                put("Армия", new ArrayList<>(emptyList()));
                put("Бригада", new ArrayList<>(Arrays.asList(
                        "Командный пункт",
                        "Запасной командный пункт"
                )));
                put("Батальон", new ArrayList<>(Collections.singletonList(
                        "Командно-наблюдательный пункт"
                )));
            }};

    private final static HashMap<String, ArrayList<String>> officialMap =
            new HashMap<String, ArrayList<String>>() {{
                put("Полк", new ArrayList<>(Arrays.asList(
                        "Командир полка",
                        "Начальник штаба полка"
                )));
                put("Армия", new ArrayList<>(emptyList()));
                put("Бригада", new ArrayList<>(Arrays.asList(
                        "Командир бригады",
                        "Помощник НОО",
                        "Начальник ОО - ЗНШ",
                        "Заместитель НОО",
                        "НШ - заместитель командира бригады",
                        "Заместитель НШ по СВиБВС",
                        "Начальник отдела БП",
                        "Помощник НОБП",
                        "Оперативный дежурный",
                        "Помощник ОД",
                        "Начальник разведки - ЗНШ по разведке",
                        "Начальник артиллерии",
                        "Начальник Разведки артиллерии",
                        "Начальник ПВО",
                        "Начальник ГБУ",
                        "Начальник инженерной службы",
                        "Начальник связи - ЗНШ по связи",
                        "Помошник начальника связи",
                        "Начальник службы РЭБ",
                        "Начальник службы РХБЗ",
                        "Начальник топографической службы ",
                        "Начальник службы - пом НШ по ЗГТ",
                        "Заместитель командира бригады",
                        "Ст помощник НОО",
                        "Помощник начальника разведки",
                        "Начальник штаба артиллерии",
                        "Помощник начальника ПВО",
                        "Зам командира бригады по вооружению",
                        "Начальник бронетанковой службы",
                        "Начальник автомобильной службы",
                        "Начальник службы РАВ",
                        "Начальник метрологической службы",
                        "Зам командира бригады по тылу",
                        "Начальник службы горючего",
                        "Начальник продовольственной службы",
                        "Начальник вещевой службы",
                        "Начальник медицинской службы"
                )));
                put("Батальон", new ArrayList<>(Arrays.asList(
                        "Командир батальона",
                        "Начальник штаба батальона",
                        "Командир минометной батареи",
                        "Авианаводчик",
                        "Командир приданного подразделения"
                )));
            }};

    public static ArrayList<String> getList(SpinnerList listName) {
        switch (listName) {
            case DIVISION_LIST:
                return divisionList;
            case DEVICE_LIST:
                return deviceList;
            case DEVICE_ROOM_LIST:
                return deviceRoomList;
            default:
                return new ArrayList<>();
        }
    }

    public static ArrayList<String> getList(SpinnerMap mapName, String key) {
        switch (mapName) {
            case OFFICIAL_MAP:
                if (!officialMap.containsKey(key)) return new ArrayList<>();
                return officialMap.get(key);
            case CONTROL_POINT_MAP:
                if (!officialMap.containsKey(key)) return new ArrayList<>();
                return controlPointMap.get(key);
            default:
                return new ArrayList<>();
        }
    }
}
