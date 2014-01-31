package hu.schonherz.y2014.partyappandroid.util.datamodell;

public class Event {
    public int id;
    public String name;
    public String description;
    public String start_date;
    public String music_type;
    public int approved;

    public Event(int id, String name, String description, String start_date, String music_type, int approved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start_date = start_date.substring(0, start_date.length() - 3);
        this.music_type = music_type;
        this.approved = approved;
    }

    public static int getMusicTypePosition(String musicType) {
        if (musicType.equals("Vegyes"))
            return 0;
        if (musicType.equals("Rock"))
            return 1;
        if (musicType.equals("Jazz"))
            return 2;
        if (musicType.equals("Electro"))
            return 3;
        if (musicType.equals("Metál"))
            return 4;
        if (musicType.equals("Mulatós"))
            return 5;
        if (musicType.equals("House"))
            return 6;
        if (musicType.equals("Dubstep"))
            return 7;
        if (musicType.equals("Pop"))
            return 8;
        if (musicType.equals("Egyéb"))
            return 9;

        return 0;
    }
}
