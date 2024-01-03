public class CustomSingletonLazy {
    private static CustomSingletonLazy instance;

    private CustomSingletonLazy() {

    }

    public static CustomSingletonLazy getInstance() {
        return HolderClass.ins;
    }

    static class HolderClass {
        private static final CustomSingletonLazy ins = new CustomSingletonLazy();
    }
}
