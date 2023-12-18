package hotciv.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Collection of constants used in HotCiv Game. Note that strings are
 * used instead of enumeration types to keep the set of valid
 * constants open to extensions by future HotCiv variants.  Enums can
 * only be changed by compile time modification.
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p>
 * Please visit http://www.baerbak.com/ for further information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class GameConstants {
    public static final int WORLDSIZE = 16; // The size of the world is set permanently to a 16x16 grid
    public static final int age_start = -4000; // age start for Iteration 1
    public static final int age_end = -3000; // age end for iteration 1

    public static final int age_increment_50 = 50;

    public static final int age_increment_25 = 25;

    public static final int age_increment_5 = 5;
    public static final int ageDelta = 100;
    // Valid unit types
    public static final String ARCHER = "archer";
    public static final String LEGION = "legion";
    public static final String SUPERLEGION = "superlegion";
    public static final String SETTLER = "settler";
    public static final String UFO = "ufo";
    // Valid terrain types
    public static final String PLAINS = "plains";
    public static final String OCEANS = "ocean";
    public static final String FOREST = "forest";
    public static final String HILLS = "hills";
    public static final String MOUNTAINS = "mountain";
    // Valid production balance types
    public static final String productionFocus = "hammer";
    public static final String foodFocus = "apple";
    public static final int unitMaxMoves = 1;
    public static final int productionGrowth = 6;

    public static final Map<String, int[]> unitInfo = new HashMap<String, int[]>() {
        {
            //Type: Cost, Defense, Attack, Movement
            put(ARCHER, new int[]{10, 3, 2, 1});
            put(LEGION, new int[]{15, 2, 4, 1});
            put(SETTLER, new int[]{30, 3, 0, 1});
            put(SUPERLEGION, new int[]{30, 2, 500, 1});
            put(UFO, new int[]{60, 8, 1, 2});
        }
    };
}
