package org.drools.phreak;

public class A {
    Integer object;

    public A(Integer object) {
        super();
        this.object = object;
    }
    
    public static A a(Integer object) {
        return new A( object );
    }

    public static A[] a(Integer... objects) {
        A[] as = new A[objects.length];
        int i = 0;
        for ( Integer object : objects ) {
            as[i++] = new A( object );
        }
        return as;
    }        

    public Object getObject() {
        return object;
    }

    public void setObject(Integer object) {
        this.object = object;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((object == null) ? 0 : object.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( getClass() != obj.getClass() ) return false;
        A other = (A) obj;
        if ( object == null ) {
            if ( other.object != null ) return false;
        } else if ( !object.equals( other.object ) ) return false;
        return true;
    }

    @Override
    public String toString() {
        return "A[" + object + "]";
    }

}