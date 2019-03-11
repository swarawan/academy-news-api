package id.investree.news.utilities.log;

public enum LogEnum {

    REQUEST {
        @Override
        public String toString() {
            return "-->";
        }
    },
    RESPONSE {
        @Override
        public String toString() {
            return "<--";
        }
    }
}
