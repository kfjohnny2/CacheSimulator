package com.example.johnnylee.cachesimulator.model.memory;

import com.example.johnnylee.cachesimulator.dto.Config;
import com.example.johnnylee.cachesimulator.model.Block;
import com.example.johnnylee.cachesimulator.model.Line;
import com.example.johnnylee.cachesimulator.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnnylee on 03/12/16.
 */

public class Cache extends Memory{
    private Line[] lines;
    private int lineAmount;
    private Config config;
    private int misses;
    private int hits;

    public Cache(Config config, int blockAmount) {
        super(blockAmount);
        this.config = config;
        this.lineAmount = config.getCacheLineSize();
        lines = new Line[lineAmount];
    }

    public String read(int cacheAdr, int wordAdr) {
        int blockPos = wordAdr / config.getBlockSize();
        int wordPos = wordAdr % config.getBlockSize();
        int linePos = -1;

        if (config.getMappingType() == 1) {
            linePos = blockPos % this.lineAmount;
        }

        if (config.getMappingType() == 2) {
            linePos = cacheAdr;
        }

        if (config.getMappingType() == 3) {
            linePos = cacheAdr;
        }

        return String.valueOf(lines[linePos].getBlock().getWordAtPosition(wordPos).getContent());
    }

    public void write(int cacheAdr, int wordAdr, String value) {
        int blockPos = wordAdr / config.getBlockSize();
        int wordPos = wordAdr % config.getBlockSize();
        int linePos = -1;

        if (config.getMappingType() == 1) {
            linePos = blockPos % config.getBlockSize();
        }

        if (config.getMappingType() == 2) {
            linePos = cacheAdr;
        }

        if (config.getMappingType() == 3) {
            linePos = cacheAdr;
        }

        // Criar linha, se não tiver sido criada.
        if (lines[linePos] == null) {
            lines[linePos] = new Line(config);
        }

        // Criar bloco, se não tiver sido criado.
        if (lines[linePos].getBlock() == null) {
            lines[linePos].setBlock(new Block(config));
        }

        // Criar Word[], se não tiver sido criada.
        if (lines[linePos].getBlock().getWords() == null) {
            lines[linePos].getBlock().setWords(new Word[config.getBlockSize()]);
        }

        // Criar Word, se não tiver sido criada.
        if (lines[linePos].getBlock().getWords()[wordPos] == null) {
            lines[linePos].getBlock().getWords()[wordPos] = new Word();
        }

        // Escrever Word na cache.
        lines[linePos].getBlock().getWordAtPosition(wordPos).setContent(value);
    }

    public String show() {
        StringBuilder sb = new StringBuilder("\n\n::: Memória cache::: ");
        sb.append("\nPosição na cache:   ");
        for (int i = 0; i < lines.length; i++) {
            sb.append(i + " ");
        }

        sb.append("\nLinhas na cache:    ");

        for (Line l : lines) {
            if (l == null) {
                sb.append("n ");
            } else {
                sb.append("" + l.getTag() + " ");
            }
        }
        List<String> tags = new ArrayList<>();
        for (Line l : lines) {
            if (l == null) {
                sb.append("n ");
            } else {
                sb.append(l.getTag() + " ");
            }
        }
        return sb.toString();
    }

    public int findOnCache(int wordAdr) {
        int blockPos = wordAdr / config.getBlockSize();
        int wordPos = wordAdr % config.getBlockSize();
        int linePos = -1;

        if (config.getMappingType() == 1) {
            linePos = blockPos % config.getCacheLineSize();

            if (lines[linePos] == null || lines[linePos].getTag() != blockPos) {
                return -1;
            }
        }

        if (config.getMappingType() == 2) {

            if (lines == null) {
                lines = new Line[config.getCacheLineSize()];
            }

            int i = 0;
            for (Line l : lines) {
                if (l != null && l.getTag() == blockPos) {
                    return i;
                }
                i++;
            }
            return -1;
        }

        if (config.getMappingType() == 3) {
            linePos = blockPos % config.getParcialSetSize();
            int lineForSet = config.getCacheLineSize() / config.getParcialSetSize();

            for (int i = linePos * config.getParcialSetSize(); i < (linePos + lineForSet + 1); i++) {
                if (lines[i] != null && lines[i].getTag() == blockPos) {
                    return i;
                }
            }
            return -1;
        }

        return linePos;
    }


    public Line[] getLines() {
        return lines;
    }

    public void setLines(Line[] lines) {
        this.lines = lines;
    }

    public int getLineAmount() {
        return lineAmount;
    }

    public void setLineAmount(int lineAmount) {
        this.lineAmount = lineAmount;
    }

    public Line getLineAtPosition(int pos){
        Line line;
        if(pos < getBlockAmount() && pos > 0)
            line = this.lines[pos];
        else
            line = null;
        return line;
    }
    public void incrementHits() {
        hits++;
    }

    public void incrementMisses() {
        misses++;
    }

    public int getMisses() {
        return misses;
    }

    public void setMisses(int misses) {
        this.misses = misses;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int nextPlace(int init, int end) {
        for (int i = init; i < end; i++) {
            if (lines[i] == null) {
                return i;
            }
        }
        return -1;
    }
}
