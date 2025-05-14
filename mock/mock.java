/*
 *classes: 

Country 
metadata 
Feature ->
 list of subfeatures 
HashMap of countries  -> is live 
Subfeature 
parentFeature 
HashMap of countries -> is live 
subfeatureLivestrategy 
featureLivetsrategy 
feature manager 

 */

public class mock {

    public static void main(String[] args)
    {
        IFeatureLiveStrategy mainFeatureLiveStrategy = new DefaultMainFeatureLiveStrategy();
        IFeatureLiveStrategy subFeatureLiveStrategy = new DefaultSubFeatureLiveStrategy();
        Feature mainFeature1 = new Feature(null, mainFeatureLiveStrategy); 
        Feature mainFeature2 = new Feature(null, mainFeatureLiveStrategy);

        Feature subFeature1 = new Feature(null, subFeatureLiveStrategy);
        Feature subFeature2 = new Feature(null, subFeatureLiveStrategy); 

        mainFeature1.addSubFeature(subFeature1);
        mainFeature2.addSubFeature(subFeature2);

        FeatureManager featureManager = FeatureManager.getInstance();
        featureManager.addFeature(mainFeature1);
        featureManager.addFeature(mainFeature2);

    }
    
}

// singleton 
class FeatureManager
{
    static FeatureManager featureManager; 
    HashMap<String, Feature> features; 

    public static FeatureManager getInstance()
    {
        if(featureManager == null) featureManager = new FeatureManager();
        return featureManager;
    }

    public FeatureManager()
    {
        features = new HashMap<>();
    }

    public void addFeature(Feature feature)
    {
        // error handling 
        features.put(feature.id, feature);
    }

}

class Country
{
    String countryName; 
    // metadata 

    public Country(String name)
    {
        this.countryName = name;
    }
}

abstract class Feature 
{
    String id;

    Map<Country, Boolean> countryMap; 

    List<Feature> subfeatures; 

    IFeatureLiveStrategy featureLiveStrategy;

    public abstract boolean isFeatureLive(Country country);

}

// composite design pattern
class MainFeature extends Feature 
{
    public MainFeature(List<Feature> subFeatures, IFeatureLiveStrategy strategy)
    {
        id = new UUID.randomUUID.toString();
        countryMap = new HashMap<>(); 
        this.subfeatures = subfeatures;
        this.featureLiveStrategy = strategy;
    }

    public boolean isFeatureLive(Country country)
    {
        return featureLiveStrategy.computeFeatureLive(this, country);
    }

    public void addSubFeature(Feature feature)
    {
        // error handling 
        subfeatures.add(feature);
    }

    public void makeFeatureLive(Country country)
    {
        if(subFeatures != null || subfeatures.size() != 0)
        {
            System.out.println("Not allowed!");
            return;
        }
        countryMap.put(country, true);
    }
}

interface IFeatureLiveStrategy
{
    public boolean computeFeatureLive(Feature feature, Country country);
}

class DefaultMainFeatureLiveStrategy
{
    public boolean computeFeatureLive(Feature feature, Country country)
    {
        // if 2 first two subfeatures are live 
        if(feature.subfeatures.size() < 2) 
        {
            System.out.println("Less than 2 sub features found!! ");
            return false; 
        }

        boolean isFirstLive = feature.subfeatures.get(0).isFeatureLive(country); 
        boolean isSecondLive = feature.subfeatures.get(1).isFeatureLive(country); 
        return isFirstLive && isSecondLive;
    }
}

class DefaultSubFeatureLiveStrategy
{
    public boolean computeFeatureLive(Feature feature, Country country)
    {
        return feature.countryMap.getOrDefault(country, false);
    }
}

