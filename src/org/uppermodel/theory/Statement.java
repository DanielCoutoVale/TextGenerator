package org.uppermodel.theory;

import java.util.LinkedList;
import java.util.List;

/**
 * A realization statement, i.e. a statement of the following kind:
 * 
 * To realize this word feature
 * - Latin speakers insert a Core
 * - Latin speakers insert a Seam
 * - Latin speakers expand the Core with a Stem
 * - Latin speakers expand the Core with a Branch
 * - Latin speakers expand the Seam with a Leaf
 * - Latin speakers place the Core before the Seam
 * - Latin speakers place the Stem before the Branch
 * - Latin speakers classify the Stem as stem
 * - Latin speakers classify the Branch as branch
 * - Latin speakers classify the Leaf as leaf
 * 
 * @author Daniel Couto-Vale
 */
public class Statement {

	private static final String A = "a".intern();
	private static final String AN = "an".intern();
	private static final String ANYWHERE = "anywhere".intern();
	private static final String AS = "as".intern();
	private static final String AT = "at".intern();
	private static final String BACK = "back".intern();
	private static final String BEFORE = "before".intern();
	private static final String CLASSIFY = "classify".intern();
	private static final String CONFLATE = "conflate".intern();
	private static final String END = "end".intern();
	private static final String EXPAND = "expand".intern();
	private static final String FOR = "for".intern();
	private static final String FRONT = "front".intern();
	private static final String INSERT = "insert".intern();
	private static final String INFLECTIFY = "inflectify".intern();
	private static final String JUST = "just".intern();
	private static final String LEXIFY = "lexify".intern();
	private static final String LIKE = "like".intern();
	private static final String THIS = "this".intern();
	private static final String UNIT = "unit".intern();
	private static final String MOULD = "mould".intern();
	private static final String NOT = "not".intern();
	private static final String OUTCLASSIFY = "outclassify".intern();
	private static final String PLACE = "place".intern();
	private static final String PREPARE = "prepare".intern();
	private static final String RIGHT = "right".intern();
	private static final String START = "start".intern();
	private static final String SUBSTANCE = "substance".intern();
	private static final String THE = "the".intern();
	private static final String WITH = "with".intern();
	
	public static enum Type {
		insert, expand, conflate, placeBefore, placeJustBefore,
		placeAtFront, placeAtBack, classify, outclassify, lexify,
		lexifyDown, inflectify, mould, prepare
	}
	
	private final String[] insert1 = {INSERT, null};
	private final String[] insert2 = {INSERT, A, null};
	private final String[] insert3 = {INSERT, AN, null};

	private final String[] expand1 = {EXPAND, null, null};
	private final String[] expand2 = {EXPAND, null, WITH, null};
	private final String[] expand3 = {EXPAND, THE, null, WITH, A, null};
	private final String[] expand4 = {EXPAND, THE, null, WITH, AN, null};

	private final String[] conflate1 = {CONFLATE, null, null};
	private final String[] conflate2 = {CONFLATE, null, WITH, null};
	private final String[] conflate3 = {CONFLATE, THE, null, WITH, THE, null};

	private final String[] placeAtFront1 = {PLACE, null, AT, FRONT};
	private final String[] placeAtFront2 = {PLACE, THE, null, AT, THE, FRONT};
	private final String[] placeAtFront3 = {PLACE, null, AT, START};
	private final String[] placeAtFront4 = {PLACE, THE, null, AT, THE, START};

	private final String[] placeAtBack1 = {PLACE, null, AT, BACK};
	private final String[] placeAtBack2 = {PLACE, THE, null, AT, THE, BACK};
	private final String[] placeAtBack3 = {PLACE, null, AT, END};
	private final String[] placeAtBack4 = {PLACE, THE, null, AT, THE, END};
	
	private final String[] placeBefore1 = {PLACE, null, BEFORE, null};
	private final String[] placeBefore2 = {PLACE, null, ANYWHERE, BEFORE, null};
	private final String[] placeBefore3 = {PLACE, THE, null, BEFORE, THE, null};
	private final String[] placeBefore4 = {PLACE, THE, null, ANYWHERE, BEFORE, THE, null};
	
	private final String[] placeJustBefore1 = {PLACE, null, JUST, BEFORE, null};
	private final String[] placeJustBefore2 = {PLACE, null, RIGHT, BEFORE, null};
	private final String[] placeJustBefore3 = {PLACE, THE, null, JUST, BEFORE, THE, null};
	private final String[] placeJustBefore4 = {PLACE, THE, null, RIGHT, BEFORE, THE, null};
	
	private final String[] classify1 = {CLASSIFY, null, null};
	private final String[] classify2 = {CLASSIFY, null, AS, null};
	private final String[] classify3 = {CLASSIFY, THE, null, AS, null};
	
	private final String[] outclassify1 = {OUTCLASSIFY, null, null};
	private final String[] outclassify2 = {OUTCLASSIFY, null, AS, null};
	private final String[] outclassify3 = {OUTCLASSIFY, THE, null, AS, null};
	private final String[] outclassify4 = {CLASSIFY, null, NOT, null};
	private final String[] outclassify5 = {CLASSIFY, null, AS, NOT, null};
	private final String[] outclassify6 = {CLASSIFY, THE, null, AS, NOT, null};
	
	private final String[] lexify1 = {LEXIFY, null, null};
	private final String[] lexify2 = {LEXIFY, null, AS, null};
	private final String[] lexify3 = {LEXIFY, THE, null, AS, null};
	
	private final String[] lexifyDown1 = {LEXIFY, null, LIKE, THIS, UNIT};
	private final String[] lexifyDown2 = {LEXIFY, THE, null, LIKE, THIS, UNIT};
	
	private final String[] inflectify1 = {INFLECTIFY, null, null};
	private final String[] inflectify2 = {INFLECTIFY, null, AS, null};
	private final String[] inflectify3 = {INFLECTIFY, THE, null, AS, null};
	
	private final String[] mould1 = {MOULD, SUBSTANCE, null};
	private final String[] mould2 = {MOULD, SUBSTANCE, AS, null};
	private final String[] mould3 = {MOULD, SUBSTANCE, WITH, null};
	private final String[] mould4 = {MOULD, SUBSTANCE, FOR, null};
	
	private final String[] prepare1 = {PREPARE, SUBSTANCE};
	
	private final String[][] statementPatterns = {
			insert1, insert2, insert3,
			expand1, expand2, expand3, expand4,
			conflate1, conflate2, conflate3,
			placeAtFront1, placeAtFront2, placeAtFront3, placeAtFront4,
			placeAtBack1, placeAtBack2, placeAtBack3, placeAtBack4,
			placeBefore1, placeBefore2, placeBefore3, placeBefore4,
			placeJustBefore1, placeJustBefore2, placeJustBefore3, placeJustBefore4,
			classify1, classify2, classify3,
			outclassify1, outclassify2, outclassify3, outclassify4, outclassify5, outclassify6,
			lexify1, lexify2, lexify3,
			lexifyDown1, lexifyDown2,
			inflectify1, inflectify2, inflectify3,
			mould1, mould2, mould3, mould4,
			prepare1
	};
	
	private List<String> tokens;
	
	public Statement(String literal) {
		tokens = new LinkedList<>();
		for (String token : literal.split(" ")) {
			tokens.add(token.intern());
		}
	}
	
	public final String operand(int index) {
		if (index < 0) throw new Error();
		if (index >= size()) return null;
		for (String[] tokenPatterns : statementPatterns) {
			if (matches(tokenPatterns)) {
				for (int i = 0; i < tokenPatterns.length; i++) {
					String tokenPattern = tokenPatterns[i];
					if (tokenPattern == null) {
						index--;
					}
					if (index == -1) {
						return tokens.get(i);
					}
				}
				throw new Error();
			}
		}
		throw new Error();
	}
	
	public final boolean valid() {
		for (String[] tokenPatterns : statementPatterns) {
			if (matches(tokenPatterns)) return true;
		}
		return false;
	}
	
	public final int size() {
		for (String[] tokenPatterns : statementPatterns) {
			if (matches(tokenPatterns)) {
				int size = 0;
				for (String tokenPattern : tokenPatterns) {
					if (tokenPattern == null) {
						size++;
					}
				}
				return size;
			}
		}
		throw new Error();
	}

	public final Type type() {
		if (matches(insert1)) return Type.insert;
		if (matches(insert2)) return Type.insert;
		if (matches(insert3)) return Type.insert;
		if (matches(expand1)) return Type.expand;
		if (matches(expand2)) return Type.expand;
		if (matches(expand3)) return Type.expand;
		if (matches(expand4)) return Type.expand;
		if (matches(conflate1)) return Type.conflate;
		if (matches(conflate2)) return Type.conflate;
		if (matches(conflate3)) return Type.conflate;
		if (matches(placeAtFront1)) return Type.placeAtFront;
		if (matches(placeAtFront2)) return Type.placeAtFront;
		if (matches(placeAtFront3)) return Type.placeAtFront;
		if (matches(placeAtFront4)) return Type.placeAtFront;
		if (matches(placeAtBack1)) return Type.placeAtBack;
		if (matches(placeAtBack2)) return Type.placeAtBack;
		if (matches(placeAtBack3)) return Type.placeAtBack;
		if (matches(placeAtBack4)) return Type.placeAtBack;
		if (matches(placeBefore1)) return Type.placeBefore;
		if (matches(placeBefore2)) return Type.placeBefore;
		if (matches(placeBefore3)) return Type.placeBefore;
		if (matches(placeBefore4)) return Type.placeBefore;
		if (matches(placeJustBefore1)) return Type.placeJustBefore;
		if (matches(placeJustBefore2)) return Type.placeJustBefore;
		if (matches(placeJustBefore3)) return Type.placeJustBefore;
		if (matches(placeJustBefore4)) return Type.placeJustBefore;
		if (matches(classify1)) return Type.classify;
		if (matches(classify2)) return Type.classify;
		if (matches(classify3)) return Type.classify;
		if (matches(outclassify1)) return Type.outclassify;
		if (matches(outclassify2)) return Type.outclassify;
		if (matches(outclassify3)) return Type.outclassify;
		if (matches(outclassify4)) return Type.outclassify;
		if (matches(outclassify5)) return Type.outclassify;
		if (matches(outclassify6)) return Type.outclassify;
		if (matches(lexify1)) return Type.lexify;
		if (matches(lexify2)) return Type.lexify;
		if (matches(lexify3)) return Type.lexify;
		if (matches(lexifyDown1)) return Type.lexifyDown;
		if (matches(lexifyDown2)) return Type.lexifyDown;
		if (matches(inflectify1)) return Type.inflectify;
		if (matches(inflectify2)) return Type.inflectify;
		if (matches(inflectify3)) return Type.inflectify;
		if (matches(mould1)) return Type.mould;
		if (matches(mould2)) return Type.mould;
		if (matches(mould3)) return Type.mould;
		if (matches(mould4)) return Type.mould;
		if (matches(prepare1)) return Type.prepare;
		return null;
	}

	private boolean matches(String[] tokenPatterns) {
		if (tokens.size() != tokenPatterns.length) {
			return false;
		}
		for (int i = 0; i < tokenPatterns.length; i++) {
			String tokenPattern = tokenPatterns[i];
			if (tokenPattern == null) continue;
			String token = tokens.get(i);
			if (tokenPattern != token) return false;
		}
		return true;
	}
	
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		for (String token : this.tokens) {
			if (buffer.length() != 0) {
				buffer.append(" ");
			}
			buffer.append(token);
		}
		return buffer.toString();
	}

}
