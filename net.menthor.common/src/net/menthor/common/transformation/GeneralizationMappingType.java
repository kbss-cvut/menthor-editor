package net.menthor.common.transformation;

public enum GeneralizationMappingType {
	allClasses {
		@Override
		public String toString() {
			return "All Classes";
		}
	}, 
	leafClasses{
		@Override
		public String toString() {
			return "Leaf Classes";
		}
	},  
	_1stClasses{
		@Override
		public String toString() {
			return "1st Classes";
		}
	};
	
//	@Override
//	public String toString() {
//		String value = "";
//		switch (this) {
//		case allClasses:
//			value = "All Classes";
//			break;
//		case leafClasses:
//			value = "Leaf Classes";
//			break;
//		case _1stClasses:
//			value = "1st Classes";
//			break;
//		}
//		return value;
//	}
	
	public static String[] valuesStr(){
		int length = GeneralizationMappingType.values().length;
		String[] values = new String[length];
		
		for (int i = 0; i < length; i++) {
			values[i] = GeneralizationMappingType.values()[i].toString();
		}
		
		return values;
	}
}