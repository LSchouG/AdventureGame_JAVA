package ai;

import adventuregame.GamePanel;
import adventuregame.entity.Entity;

import java.util.ArrayList;

public class PathFinder {
    public ArrayList<Node> pathList = new ArrayList<>();
    ArrayList<Node> touchedNodes = new ArrayList<>();
    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNodes();
    }
    public void instantiateNodes() {
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        for (int col = 0; col < gp.maxWorldCol; col++) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
                node[col][row] = new Node(col, row);

                int tileNum = gp.tileM.mapTileNumber[gp.currentMap][col][row];
                if (gp.tileM.tile[tileNum].collision) {
                    node[col][row].solid = true; // permanent walls set once
                }
            }
        }
    }
    public void resetNodes() {
        for (Node n : touchedNodes) {
            n.open = false;
            n.checked = false;
            n.parent = null;
            n.gCost = 0;
            n.hCost = 0;
            n.fCost = 0;
        }
        touchedNodes.clear();

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        // only check interactive tiles
        for (int i = 0; i < gp.iTile[gp.currentMap].length; i++) {
            if (gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible) {
                int itCol = gp.iTile[gp.currentMap][i].worldX / gp.tileSize;
                int itRow = gp.iTile[gp.currentMap][i].worldY / gp.tileSize;
                node[itCol][itRow].solid = true;
            }
        }

        // compute costs for nodes on the fly in `openNode()`, not all at once
    }
    public void getCost(Node node) {
        // G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        // H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        // F cost
        node.fCost = node.gCost + node.hCost;
    }
    public boolean search() {
        while (goalReached == false && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            // Check the current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // Open the up node
            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            // Open the left node
            if (col - 1 >= 0) {
                openNode(node[col-1][row]);
            }
            // Open the down node
            if (row + 1 < gp.maxWorldRow) {
                openNode(node[col][row + 1]);
            }
            // Open the right node
            if (col + 1 < gp.maxWorldCol) {
                openNode(node[col + 1][row]);
            }

            // Find the best node
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;
            for(int i = 0; i < openList.size(); i++){
                // Check if this node F cost id better
                if(openList.get(i).fCost <bestNodeFCost){
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                }
                else if (openList.get(i).fCost == bestNodeFCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            // If there is no node in the  openList end the loop
            if(openList.size() == 0){
                break;
            }
            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }
    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            getCost(node); // compute cost only when used
            openList.add(node);
            touchedNodes.add(node); // mark for later reset
        }
    }
    public void trackThePath(){
        Node current = goalNode;
        while(current != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }
} // END class PathFinder
