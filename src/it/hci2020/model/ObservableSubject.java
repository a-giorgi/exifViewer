package it.hci2020.model;

import it.hci2020.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ObservableSubject { //implementing multiple aspects: see GOF Design Patterns, Observer implementation (pull)
    protected HashMap<Aspects,ArrayList<Observer>> observersMap = new HashMap<Aspects,ArrayList<Observer>>();

    public void attach(Observer o, Aspects aspect){
        if(observersMap.containsKey(aspect)){
            observersMap.get(aspect).add(o);
        }else{
            ArrayList<Observer> newAspectList = new ArrayList<Observer>();
            newAspectList.add(o);
            observersMap.put(aspect,newAspectList);
        }
    }
    public void detach(Observer o, Aspects aspect) throws NoSuchFieldException{
        if(!observersMap.containsKey(aspect)){
            throw new NoSuchFieldException("Aspect not found!");
        }
        observersMap.get(aspect).remove(o);
    }
    public void notify(Observer o){
        o.update();
    }
    public void notify(Aspects aspect) throws NoSuchFieldException {
        if(!observersMap.containsKey(aspect)){
            throw new NoSuchFieldException("Aspect not found!");
        }
        ArrayList<Observer> observerToNotify = observersMap.get(aspect);
        for(Observer o: observerToNotify){
            o.update();
        }
    }

}

