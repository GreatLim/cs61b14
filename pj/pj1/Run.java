public class Run {
  public short red;
  public short green;
  public short blue;
  public long length;


  public Run(short red, short green, short blue, long length){
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.length = length;
  }

  public Run(long length){
    this((short) 0, (short) 0, (short) 0, length);
  }

  public Run(Run i){
    this.red = i.red;
    this.green = i.green;
    this.blue = i.blue;
    this.length = i.length;
  }

}
