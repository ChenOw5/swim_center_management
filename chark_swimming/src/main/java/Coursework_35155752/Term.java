package Coursework_35155752;

public class Term {
    private final int term_id;
    private final String terms;

    public Term(int term_id, String terms) {
        this.term_id = term_id;
        this.terms = terms;
    }

    public int getTerm_id() {
        return term_id;
    }

    public String getTerms() {
        return terms;
    }
}
