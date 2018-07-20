package com.ava.frame.abnf.antlr4;// Generated from E:/workspace/changhong/frame/frame-abnf/src/extends/antlr4\Abnf.g4 by ANTLR 4.6
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AbnfLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, NumberValue=8, 
		ProseValue=9, Regex=10, ID=11, INT=12, COMMENT=13, WS=14, STRING=15;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "NumberValue", 
		"BinaryValue", "DecimalValue", "HexValue", "ProseValue", "Regex", "ID", 
		"INT", "COMMENT", "WS", "STRING", "BIT", "DIGIT", "HEX_DIGIT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "'/'", "'*'", "'('", "')'", "'['", "']'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, "NumberValue", "ProseValue", 
		"Regex", "ID", "INT", "COMMENT", "WS", "STRING"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public AbnfLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Abnf.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\21\u00ce\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\5\t@\n\t\3\n\3\n\6\n"+
		"D\n\n\r\n\16\nE\3\n\3\n\6\nJ\n\n\r\n\16\nK\6\nN\n\n\r\n\16\nO\3\n\3\n"+
		"\6\nT\n\n\r\n\16\nU\5\nX\n\n\3\13\3\13\6\13\\\n\13\r\13\16\13]\3\13\3"+
		"\13\6\13b\n\13\r\13\16\13c\6\13f\n\13\r\13\16\13g\3\13\3\13\6\13l\n\13"+
		"\r\13\16\13m\5\13p\n\13\3\f\3\f\6\ft\n\f\r\f\16\fu\3\f\3\f\6\fz\n\f\r"+
		"\f\16\f{\6\f~\n\f\r\f\16\f\177\3\f\3\f\6\f\u0084\n\f\r\f\16\f\u0085\5"+
		"\f\u0088\n\f\3\r\3\r\7\r\u008c\n\r\f\r\16\r\u008f\13\r\3\r\3\r\3\16\3"+
		"\16\7\16\u0095\n\16\f\16\16\16\u0098\13\16\3\16\3\16\3\17\3\17\7\17\u009e"+
		"\n\17\f\17\16\17\u00a1\13\17\3\20\6\20\u00a4\n\20\r\20\16\20\u00a5\3\21"+
		"\3\21\7\21\u00aa\n\21\f\21\16\21\u00ad\13\21\3\21\5\21\u00b0\n\21\3\21"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\5\23\u00be\n\23"+
		"\3\23\3\23\7\23\u00c2\n\23\f\23\16\23\u00c5\13\23\3\23\3\23\3\24\3\24"+
		"\3\25\3\25\3\26\3\26\2\2\27\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\2\25"+
		"\2\27\2\31\13\33\f\35\r\37\16!\17#\20%\21\'\2)\2+\2\3\2\n\3\2@@\3\2BB"+
		"\4\2C\\c|\7\2//\62;C\\aac|\4\2\f\f\17\17\5\2\13\f\17\17\"\"\3\2$$\5\2"+
		"\62;CHch\u00e4\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\3-\3\2\2\2"+
		"\5/\3\2\2\2\7\61\3\2\2\2\t\63\3\2\2\2\13\65\3\2\2\2\r\67\3\2\2\2\179\3"+
		"\2\2\2\21;\3\2\2\2\23A\3\2\2\2\25Y\3\2\2\2\27q\3\2\2\2\31\u0089\3\2\2"+
		"\2\33\u0092\3\2\2\2\35\u009b\3\2\2\2\37\u00a3\3\2\2\2!\u00a7\3\2\2\2#"+
		"\u00b5\3\2\2\2%\u00bd\3\2\2\2\'\u00c8\3\2\2\2)\u00ca\3\2\2\2+\u00cc\3"+
		"\2\2\2-.\7?\2\2.\4\3\2\2\2/\60\7\61\2\2\60\6\3\2\2\2\61\62\7,\2\2\62\b"+
		"\3\2\2\2\63\64\7*\2\2\64\n\3\2\2\2\65\66\7+\2\2\66\f\3\2\2\2\678\7]\2"+
		"\28\16\3\2\2\29:\7_\2\2:\20\3\2\2\2;?\7\'\2\2<@\5\23\n\2=@\5\25\13\2>"+
		"@\5\27\f\2?<\3\2\2\2?=\3\2\2\2?>\3\2\2\2@\22\3\2\2\2AC\7d\2\2BD\5\'\24"+
		"\2CB\3\2\2\2DE\3\2\2\2EC\3\2\2\2EF\3\2\2\2FW\3\2\2\2GI\7\60\2\2HJ\5\'"+
		"\24\2IH\3\2\2\2JK\3\2\2\2KI\3\2\2\2KL\3\2\2\2LN\3\2\2\2MG\3\2\2\2NO\3"+
		"\2\2\2OM\3\2\2\2OP\3\2\2\2PX\3\2\2\2QS\7/\2\2RT\5\'\24\2SR\3\2\2\2TU\3"+
		"\2\2\2US\3\2\2\2UV\3\2\2\2VX\3\2\2\2WM\3\2\2\2WQ\3\2\2\2WX\3\2\2\2X\24"+
		"\3\2\2\2Y[\7f\2\2Z\\\5)\25\2[Z\3\2\2\2\\]\3\2\2\2][\3\2\2\2]^\3\2\2\2"+
		"^o\3\2\2\2_a\7\60\2\2`b\5)\25\2a`\3\2\2\2bc\3\2\2\2ca\3\2\2\2cd\3\2\2"+
		"\2df\3\2\2\2e_\3\2\2\2fg\3\2\2\2ge\3\2\2\2gh\3\2\2\2hp\3\2\2\2ik\7/\2"+
		"\2jl\5)\25\2kj\3\2\2\2lm\3\2\2\2mk\3\2\2\2mn\3\2\2\2np\3\2\2\2oe\3\2\2"+
		"\2oi\3\2\2\2op\3\2\2\2p\26\3\2\2\2qs\7z\2\2rt\5+\26\2sr\3\2\2\2tu\3\2"+
		"\2\2us\3\2\2\2uv\3\2\2\2v\u0087\3\2\2\2wy\7\60\2\2xz\5+\26\2yx\3\2\2\2"+
		"z{\3\2\2\2{y\3\2\2\2{|\3\2\2\2|~\3\2\2\2}w\3\2\2\2~\177\3\2\2\2\177}\3"+
		"\2\2\2\177\u0080\3\2\2\2\u0080\u0088\3\2\2\2\u0081\u0083\7/\2\2\u0082"+
		"\u0084\5+\26\2\u0083\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0083\3\2"+
		"\2\2\u0085\u0086\3\2\2\2\u0086\u0088\3\2\2\2\u0087}\3\2\2\2\u0087\u0081"+
		"\3\2\2\2\u0087\u0088\3\2\2\2\u0088\30\3\2\2\2\u0089\u008d\7>\2\2\u008a"+
		"\u008c\n\2\2\2\u008b\u008a\3\2\2\2\u008c\u008f\3\2\2\2\u008d\u008b\3\2"+
		"\2\2\u008d\u008e\3\2\2\2\u008e\u0090\3\2\2\2\u008f\u008d\3\2\2\2\u0090"+
		"\u0091\7@\2\2\u0091\32\3\2\2\2\u0092\u0096\7B\2\2\u0093\u0095\n\3\2\2"+
		"\u0094\u0093\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097"+
		"\3\2\2\2\u0097\u0099\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009a\7B\2\2\u009a"+
		"\34\3\2\2\2\u009b\u009f\t\4\2\2\u009c\u009e\t\5\2\2\u009d\u009c\3\2\2"+
		"\2\u009e\u00a1\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\36"+
		"\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2\u00a4\4\62;\2\u00a3\u00a2\3\2\2\2\u00a4"+
		"\u00a5\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6 \3\2\2\2"+
		"\u00a7\u00ab\7%\2\2\u00a8\u00aa\n\6\2\2\u00a9\u00a8\3\2\2\2\u00aa\u00ad"+
		"\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad"+
		"\u00ab\3\2\2\2\u00ae\u00b0\7\17\2\2\u00af\u00ae\3\2\2\2\u00af\u00b0\3"+
		"\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\7\f\2\2\u00b2\u00b3\3\2\2\2\u00b3"+
		"\u00b4\b\21\2\2\u00b4\"\3\2\2\2\u00b5\u00b6\t\7\2\2\u00b6\u00b7\3\2\2"+
		"\2\u00b7\u00b8\b\22\2\2\u00b8$\3\2\2\2\u00b9\u00ba\7\'\2\2\u00ba\u00be"+
		"\7u\2\2\u00bb\u00bc\7\'\2\2\u00bc\u00be\7k\2\2\u00bd\u00b9\3\2\2\2\u00bd"+
		"\u00bb\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c3\7$"+
		"\2\2\u00c0\u00c2\n\b\2\2\u00c1\u00c0\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3"+
		"\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c6\3\2\2\2\u00c5\u00c3\3\2"+
		"\2\2\u00c6\u00c7\7$\2\2\u00c7&\3\2\2\2\u00c8\u00c9\4\62\63\2\u00c9(\3"+
		"\2\2\2\u00ca\u00cb\4\62;\2\u00cb*\3\2\2\2\u00cc\u00cd\t\t\2\2\u00cd,\3"+
		"\2\2\2\33\2?EKOUW]cgmou{\177\u0085\u0087\u008d\u0096\u009f\u00a5\u00ab"+
		"\u00af\u00bd\u00c3\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}