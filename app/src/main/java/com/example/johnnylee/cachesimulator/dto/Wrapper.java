package com.example.johnnylee.cachesimulator.dto;

import com.example.johnnylee.cachesimulator.dto.commands.Read;
import com.example.johnnylee.cachesimulator.dto.commands.Write;
import com.example.johnnylee.cachesimulator.model.Block;
import com.example.johnnylee.cachesimulator.model.Line;
import com.example.johnnylee.cachesimulator.model.Word;
import com.example.johnnylee.cachesimulator.model.memory.Cache;
import com.example.johnnylee.cachesimulator.model.memory.Main;

/**
 * Created by johnnylee on 05/12/16.
 */

public class Wrapper {

    private Config config;
    private Main main;
    private Cache cache;

    public Wrapper(Config config) {
        this.config = config;
        cache = new Cache(config);
        main = new Main(config);
    }

    public void write(Write write) {
        int cacheAddress = cache.find(write.getAddress());
        if (cacheAddress != -1) {
            cache.cacheHit();
            if (config.getWritingType() == 1) {
                cache.write(cacheAddress, write.getAddress(), write.getValue());
            }

            if (config.getWritingType() == 2) {
                cache.write(cacheAddress, write.getAddress(), write.getValue());
                main.write(write.getAddress(), write.getValue());
            }
        } else {
            cache.cacheMiss();

            if (config.getWritingType() == 1) {
                cacheAddress = moveBlockToCache(write.getAddress());
                cache.write(cacheAddress, write.getAddress(), write.getValue());
            }

            if (config.getWritingType() == 2) {
                cacheAddress = moveBlockToCache(write.getAddress());
                cache.write(cacheAddress, write.getAddress(), write.getValue());
                main.write(write.getAddress(), write.getValue());
            }
        }
    }

    public String read(Read read) {
        int cacheAddress = cache.find(read.getAddress());

        if (cacheAddress != -1) {
            cache.cacheHit();

            return cache.read(cacheAddress, read.getAddress());
        } else {
            cache.cacheMiss();

            cacheAddress = moveBlockToCache(read.getAddress());
            return cache.read(cacheAddress, read.getAddress());
        }
    }

    public String show() {
        return main.show() + "\n " + cache.show() + "\n\nHits: " + cache.getHits()
                + "\nMisses: " + cache.getMisses() + "\n\n";
    }

    private int moveBlockToCache(int wordAddress) {
        int blockPos = wordAddress / cache.getConfig().getCacheLineSize();
        int linePos = -1;

        if (main.getBlocks()[blockPos] == null) {
            main.getBlocks()[blockPos] = new Block(config);
        }

        if (cache.getConfig().getMappingType() == 1) {
            linePos = blockPos % cache.getConfig().getCacheLineSize();
        }

        if (cache.getConfig().getMappingType() == 2) {
            if (linePos == -1) {
                linePos = cache.nextPlace(0, cache.getLines().length);

//                if (linePos == -1) {
//                }
            }
        }

        if (cache.getConfig().getMappingType() == 3) {
            linePos = blockPos % cache.getConfig().getParcialSetSize();
            int lineForSet = config.getCacheLineSize() / config.getParcialSetSize();

            linePos = cache.nextPlace(linePos * config.getParcialSetSize(), linePos + lineForSet + 1);

//            if (linePos == -1) {
//            }
        }

        if (cache.getLines() == null) {
            cache.setLines(new Line[config.getCacheLineSize()]);
        }

        if (cache.getLineAtPosition(linePos) == null) {
            cache.getLines()[linePos] = new Line(config);
        }

        if (cache.getLineAtPosition(linePos).getBlock() == null) {
            cache.getLineAtPosition(linePos).setBlock(new Block(config));
        }

        if (cache.getConfig().getWritingType() == 1) {

            int oldPos = cache.getLines()[linePos].getTag();

            if (main.getBlocks()[oldPos] == null) {
                main.getBlocks()[oldPos] = new Block(config);
            }

            if (main.getBlocks()[oldPos].getWords() == null) {
                main.getBlocks()[oldPos].setWords(new Word[config.getBlockSize()]);
            }

            for (int i = 0; i < config.getBlockSize(); i++) {
                if (cache.getLines()[linePos].getBlock().getWordAtPosition(i) == null) {
                    cache.getLines()[linePos].getBlock().setWordAt(i, "");
                }

                if (main.getBlocks()[oldPos].getWordAtPosition(i) == null) {
                    main.getBlocks()[oldPos].setWordAt(i, "");
                }

                main.getBlocks()[oldPos].getWordAtPosition(i).setContent(cache.getLines()[linePos]
                        .getBlock().getWordAtPosition(i).getContent());
            }
        }

        cache.getLines()[linePos].setTag(blockPos);
        cache.getLines()[linePos].setHits(0);

        for (int i = 0; i < config.getBlockSize(); i++) {
            if (cache.getLines()[linePos].getBlock().getWordAtPosition(i) == null) {
                cache.getLines()[linePos].getBlock().setWordAt(i, "");
            }

            if (main.getBlocks()[blockPos].getWordAtPosition(i) == null) {
                main.getBlocks()[blockPos].setWordAt(i, "");
            }

            cache.getLines()[linePos].getBlock().getWordAtPosition(i).setContent(main.getBlocks()[blockPos].getWordAtPosition(i).getContent());
        }

        return linePos;
    }
}
