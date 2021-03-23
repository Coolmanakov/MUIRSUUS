package com.example.muirsuus.main_navigation.calculation.SubscriberNetwork;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Database(entities = {
        SubscriberNetworkDatabase.Division.class,
        SubscriberNetworkDatabase.Official.class,
        SubscriberNetworkDatabase.Device.class,
        SubscriberNetworkDatabase.DeviceRoom.class,
        SubscriberNetworkDatabase.DeviceEntry.class},
        version = 1)
public abstract class SubscriberNetworkDatabase extends RoomDatabase {
    private static SubscriberNetworkDatabase INSTANCE;
    public abstract SubscriberNetworkDBDao dao();

    public static SubscriberNetworkDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    SubscriberNetworkDatabase.class, "SubscriberNetwork.db")
                    .createFromAsset("subscriber_network_asset.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    @Entity(tableName = "division")
    public static class Division {

        @Nullable
        @PrimaryKey
        public Long id;

        @NonNull
        public String name;

        public Division(@NotNull String name) {
            this.name = name;
        }
    }

    @Entity(tableName = "official",
            foreignKeys =
    @ForeignKey(entity = Division.class, parentColumns = "id", childColumns = "division_id"))
    public static class Official {

        @Nullable
        @PrimaryKey
        public Long id;

        @NonNull
        public String name;

        @NonNull
        @ColumnInfo(name = "division_id")
        public Long divisionId;

        public Official(@NotNull String name, long divisionId) {
            this.name = name;
            this.divisionId = divisionId;
        }
    }

    @Entity(tableName = "device")
    public static class Device {

        @Nullable
        @PrimaryKey
        public Long id;

        @NonNull
        public String name;

        public Device(@NotNull String name) {
            this.name = name;
        }
    }

    @Entity(tableName = "device_room")
    public static class DeviceRoom {

        @Nullable
        @PrimaryKey
        public Long id;

        @NonNull
        public String name;

        public DeviceRoom(@NotNull String name) {
            this.name = name;
        }
    }

    @Entity(tableName = "device_entry",
            foreignKeys = {
            @ForeignKey(entity = DeviceRoom.class, parentColumns = "id", childColumns = "device_room_id"),
            @ForeignKey(entity = Device.class, parentColumns = "id", childColumns = "device_id")
    })
    public static class DeviceEntry {

        @Nullable
        @PrimaryKey
        public Long id;

        @NonNull
        @ColumnInfo(name = "device_room_id")
        public Long deviceRoomId;

        @NonNull
        @ColumnInfo(name = "device_id")
        public Long deviceId;

        @NonNull
        @ColumnInfo(name = "device_count")
        public Integer deviceCount;

        public DeviceEntry(long deviceRoomId, long deviceId, int deviceCount) {
            this.deviceRoomId = deviceRoomId;
            this.deviceId = deviceId;
            this.deviceCount = deviceCount;
        }
    }

    @Dao
    public interface SubscriberNetworkDBDao {
        @Query("SELECT name FROM division")
        List<String> getAllDivisions();

        @Query("SELECT name FROM official " +
                "WHERE division_id IN (SELECT id FROM division WHERE name = :division)")
        List<String> getOfficialByDivision(String division);
        
        @Query("SELECT name FROM device")
        List<String> getAllDevices();

        @Query("SELECT name FROM device WHERE id = :id")
        String getDeviceById(long id);

        @Query("SELECT name FROM device_room WHERE id = :id")
        String getDeviceRoomById(long id);

        @Query("SELECT * FROM device_entry " +
                "WHERE device_id IN (SELECT id FROM device WHERE name = :device)")
        List<DeviceEntry> getDeviceEntryByDevice(String device);

        @Query("SELECT * FROM device_entry " +
                "WHERE device_room_id IN (SELECT id FROM device_room WHERE name = :deviceRoom)")
        List<DeviceEntry> getDeviceEntryByDeviceRoom(String deviceRoom);
    }
}


