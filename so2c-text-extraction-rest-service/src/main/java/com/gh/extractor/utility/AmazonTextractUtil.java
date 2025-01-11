package com.gh.extractor.utility;

import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.GetDocumentAnalysisResult;
import com.amazonaws.services.textract.model.Relationship;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AmazonTextractUtil {
    public static Map<String, String> extractDataFromDocumentAnalysisResult(GetDocumentAnalysisResult getDocumentAnalysisResult) {
        return getOcrKeyValueMap(getDocumentAnalysisResult);
    }
    private static Map<String, String> getOcrKeyValueMap(GetDocumentAnalysisResult getDocumentAnalysisResult) {

        Map<String, Block> keyMap = new HashMap<>();
        Map<String, Block> valuesMap = new HashMap<>();
        Map<String, Block> totalBlocksMap = new HashMap<>();
        for (Block block : getDocumentAnalysisResult.getBlocks()) {
            String blockId = block.getId().trim();
            totalBlocksMap.put(blockId, block);

            if (block.getBlockType().equals("KEY_VALUE_SET")) {
                if (block.getEntityTypes().contains("KEY")) {
                    keyMap.put(blockId, block);
                } else {
                    valuesMap.put(blockId, block);
                }
            } else if (block.getBlockType().equals("QUERY")) {
                if (block.getRelationships() != null && !block.getRelationships().isEmpty()) {
                    keyMap.put(blockId, block);
                }
            } else if (block.getBlockType().equals("QUERY_RESULT")) {
                valuesMap.put(blockId, block);
            }
        }
        return generateKeyValueMap(keyMap, valuesMap, totalBlocksMap);
    }
    private static Map<String, String> generateKeyValueMap(Map<String, Block> keyMap,
                                                           Map<String, Block> valueMap, Map<String, Block> blockMap) {
        Map<String, Block> keyValueMap = new HashMap<>();

        for (var key : keyMap.entrySet()) {
            Block valueBlock = getValueBlock(key.getValue(), valueMap);

            if (valueBlock != null) {
                String keyValToUpper = getTextForValue(key.getValue(), blockMap).toUpperCase();
                Block existingValBlock = keyValueMap.get(keyValToUpper);

                //TODO:  Need to test if this logic is consistently
                // accurate:  The higher a value is on the page, the more "primary" it is considered (i.e. primary vs secondary insurance)
                if (existingValBlock == null ||
                        (valueBlock.getGeometry().getBoundingBox().getTop() < existingValBlock.getGeometry().getBoundingBox().getTop())) {
                        keyValueMap.put(keyValToUpper, valueBlock);
                }
            }
        }
        return keyValueMap.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> getTextForValue(e.getValue(), blockMap)));
    }
    private static Block getValueBlock(Block keyBlock, Map<String, Block> valueMap) {
        Block valBlock = null;

        if (keyBlock.getRelationships() != null) {
            for (Relationship relationship : keyBlock.getRelationships()) {
                if (relationship.getType().equals("VALUE")  || relationship.getType().equals("ANSWER")) {
                    for (String valueIds : relationship.getIds()) {
                        valBlock = valueMap.get(valueIds);
                    }
                }
            }
        }
        return valBlock;
    }
    private static String getTextForValue(Block block, Map<String, Block> totalBlocks) {
        String text = "";
        Block wordBlock;

        if (block.getBlockType().equals("QUERY")) {
            return block.getQuery().getAlias();
        } else if (block.getBlockType().equals("QUERY_RESULT")) {
            return block.getText();
        }

        if (block.getRelationships() != null) {
            for (Relationship relationship : block.getRelationships()) {
                if (relationship.getType().equals("CHILD")) {
                    for (String childId : relationship.getIds()) {
                        wordBlock = totalBlocks.get(childId);

                        if (wordBlock.getBlockType().equals("WORD")) {
                            text += wordBlock.getText() + " ";
                        } else if (wordBlock.getBlockType().equals("SELECTION_ELEMENT")) {
                            if (wordBlock.getSelectionStatus().equals("SELECTED")) {
                                text += "X ";
                            }
                        }
                    }
                }
            }
        }
        return text.trim();
    }
}
