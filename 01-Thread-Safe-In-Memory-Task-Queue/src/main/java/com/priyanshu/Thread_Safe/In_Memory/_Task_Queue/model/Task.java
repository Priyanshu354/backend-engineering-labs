package com.priyanshu.Thread_Safe.In_Memory._Task_Queue.model;

import com.priyanshu.Thread_Safe.In_Memory._Task_Queue.enums.Priority;
import lombok.Data;

import java.time.Instant;

@Data
public class Task {
    private String task;
    private Instant timestamp;
    private Priority priority;
}
