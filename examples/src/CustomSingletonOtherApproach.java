public class CustomSingletonOtherApproach {
    private static final CustomSingletonOtherApproach instance =  new CustomSingletonOtherApproach();

    private CustomSingletonOtherApproach() {
    }

    public static CustomSingletonOtherApproach getInstance() {
        // drawback is that instance is eagerly created when it was not even needed.
        return instance;
    }

}
