package pl.edu.pg.apkademikbackend.noticeboard.model;

public class NoticeBoardDto {

    private  String name;
    private long dormId;

    public NoticeBoardDto(){

    }
    public NoticeBoardDto(String name,long dormId){
        this.name=name;
        this.dormId=dormId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public long getDormId(){return dormId;}
    public void setDormId(long dormId){this.dormId=dormId;}
}
