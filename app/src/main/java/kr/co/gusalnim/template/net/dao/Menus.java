package kr.co.gusalnim.template.net.dao;

import java.util.List;

public class Menus {

    public List<OneDep> oneDep;

    public static class OneDep {
        private String title;
        private String color;
        private String icon;
        private String arr;
        public List<TwoDep> twoDep;

        public OneDep(String title, String color, String icon, String arr, List<TwoDep> items) {
            this.title = title;
            this.color = color;
            this.icon = icon;
            this.arr = arr;
            this.twoDep = items;
        }

        public String getTitle() {
            return title;
        }

        public String getColor() {
            return color;
        }

        public String getIcon() {
            return icon;
        }

        public String getArr() {
            return arr;
        }

        public List<TwoDep> getTwoDep() {
            return twoDep;
        }

        public static class TwoDep {
            private String title;
            private String icon;
            private String idName;
            public TwoDep(String title, String icon, String idName) {
                this.title = title;
                this.icon = icon;
                this.idName = idName;
            }

            public String getTitle() {
                return title;
            }

            public String getIcon() {
                return icon;
            }

            public String getIdName() {
                return idName;
            }
        }

        public void setTwoDep(List<TwoDep> twoDep) {
            this.twoDep = twoDep;
        }
    }

}