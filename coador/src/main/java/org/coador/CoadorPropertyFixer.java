package org.coador;

public interface CoadorPropertyFixer {

    public static class NOPF implements CoadorPropertyFixer {

        @Override
        public String fixProperty(String propertyName, Class<?> clazz) {
            // TODO Auto-generated method stub
            return null;
        }

    }

    public static final CoadorPropertyFixer NOP = new NOPF();

    String fixProperty(String propertyName, Class<?> clazz);

}
