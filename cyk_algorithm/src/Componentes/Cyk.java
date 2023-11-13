package Componentes;

/**
 * G:
 * S -> NP VP
 * VP -> VP PP
 * VP -> V NP
 * VP -> come
 * PP -> P NP
 * NP -> Det N
 * NP -> ela
 * V -> come
 * P -> com
 * N -> peixe
 * N -> garfo
 * Det -> um
 * 
 * w: ela come um peixe com um garfo
 */

/**
 * input: a CFG G = (N, Σ, S, ->) in CNF, a word w = a1 . . . an ∈Σ+
 * CYK(G,w) =
 * for i = 1, . . . , n do
 * Ti,i := {A ∈N |A -> ai}
 * for j = 2, . . . , n do
 * for i = j - 1, . . . , 1 do
 * Ti,j := ∅;
 * for h = i, . . . , j - 1 do
 * for all A -> BC
 * if B ∈Ti,h and C ∈Th+1,j then
 * Ti,j := Ti,j ∪ {A}
 * 
 * if S ∈T1,n then return yes else return no
 */

// package Componentes;

// public class Cyk {

// }
