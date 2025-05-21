/*
 Consider there are different types of alexa devices available. One with audio, one with screen, one with audio and screen. These devices may have a battery or may not. Battery devices will have battery percentage. Both battery and non battery devices can be put charging. The task is to show the battery percentage. Include a show methond and that method should show the current battery percentage if it has a battery. If not just say, battery not available. You should also say whether its currently charging or not. There will four statements to print show method like Charging and battery percentage, charging and no battery, just battery percent and no battery.

 audio 
 screen 
 audio and screen 

 they have battery -> battery percentages 
 or no battery

 all devices could be charged 

 audio and screen as decorator 
 battery as interface 
 charging as boolean 
 */

public class amazondevices {
    public static void main(String[] args)
    {
        Alexa batteryAlexa = new BasicAlexa(new HasBattery(45), true);
        Alexa noBattery = new BasicAlexa(new NoBattery(), false);

        Alexa audioDevice = new AudioAlexaDecorator(batteryAlexa);
       audioDevice.show(); 

       Alexa screenAndAudio = new ScreenAlexaDecorator(audioDevice);
       screenAndAudio.show();

       Alexa screenAlexa = new ScreenAlexaDecorator(noBattery);
       screenAlexa.show();
    }
 }
    


interface BatteryStatus
{
    String getBatteryInfo();
}

class HasBattery implements BatteryStatus
{
    private int percentage;

    public HasBattery(int batteryPercent)
    {
        this.percentage = batteryPercent;
    }

    @Override
    public String getBatteryInfo()
    {
        return "Battery: " + percentage + "%";
    }
}

class NoBattery implements BatteryStatus
{
    @Override
    public String getBatteryInfo()
    {
        return "No Battery";
    }
}

interface Alexa
{
    void show(); 
}

class BasicAlexa implements Alexa 
{
    BatteryStatus batteryStatus;
    Boolean isCharging;

    public BasicAlexa(BatteryStatus batteryStatus, Boolean isCharging)
    {
        this.batteryStatus = batteryStatus;
        this.isCharging = isCharging;
    }

    public void setCharging()
    {
        this.isCharging = true;
    }

    @Override 
    public void show()
    {
        System.out.println(batteryStatus.getBatteryInfo());
        System.out.println("Is charging: " + isCharging);
    }
}

abstract class AlexaDecorator implements Alexa
{
    Alexa alexa; 
    public AlexaDecorator(Alexa alexa)
    {
        this.alexa = alexa;
    }

    @Override
    public void show()
    {
        alexa.show();
    }
}

class AudioAlexaDecorator extends AlexaDecorator
{
    public AudioAlexaDecorator(Alexa alexa)
    {
        super(alexa);
    }

    @Override
    public void show()
    {
        System.out.println("This is an audio device!!");
        alexa.show();
    }
}

class ScreenAlexaDecorator extends AlexaDecorator
{
    public ScreenAlexaDecorator(Alexa alexa)
    {
        super(alexa);
    }

    @Override
    public void show()
    {
        System.out.println("This is a screen device!!");
        alexa.show();
    }
}