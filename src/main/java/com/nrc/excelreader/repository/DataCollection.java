package com.nrc.excelreader.repository;

import com.nrc.excelreader.model.Participant;

import java.util.ArrayList;
import java.util.List;

/**
 * Temporary data store collection
 * Date : 27.08.2023
 * Version : 1.0 (Initial Version)
 * @author Ujwala Vanve
 */

public class DataCollection {

    public List<Integer> nameColumnIndexList = new ArrayList<>();
    public List<Integer> dateOfBirthColumnIndexList = new ArrayList<>();
    public List<Integer> addressColumnIndexList = new ArrayList<>();
    public List<Integer> phoneNumberColumnIndexList = new ArrayList<>();

    private final List<Participant> participantsList = new ArrayList<>();

    public List<Participant> getParticipantList() {
        return participantsList;
    }

    public Participant addParticipant(String participantName) {
        Participant participant = new Participant(participantName);
        participantsList.add(participant);
        return participant;
    }



}
