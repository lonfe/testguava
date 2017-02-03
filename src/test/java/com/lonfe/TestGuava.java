package com.lonfe;import com.google.common.base.*;import com.google.common.collect.ImmutableSortedSet;import com.google.common.collect.Lists;import com.google.common.collect.Ordering;import org.junit.Test;import java.util.Arrays;import java.util.Iterator;import java.util.List;import java.util.UUID;public class TestGuava {	@Test	public void testPreconditions() {		/**		 * checkArgument(boolean)	检查boolean是否为true，用来检查传递给方法的参数。	IllegalArgumentException		 */		boolean flag = true;		Preconditions.checkArgument(flag, "flag is false");		/**		 * checkNotNull(T)	检查value是否为null，该方法直接返回value，因此可以内嵌使用checkNotNull。	NullPointerException		 */		String str = "abc";		Preconditions.checkNotNull(str, "obj is null").toString();		/**		 * checkState(boolean)	用来检查对象的某些状态。	IllegalStateException		 */		Preconditions.checkState(flag, "flag is false");		String[] strs = {"1", "2", "3", "4", "5"};		for (int i = 1; i < strs.length + 1; i++) {			/**			 * checkElementIndex(int index, int size)	检查index作为索引值对某个列表、字符串或数组是否有效。index>=0 && index<size *	IndexOutOfBoundsException			 */			Preconditions.checkElementIndex(i, i+1);			/**			 * checkPositionIndex(int index, int size)	检查index作为位置值对某个列表、字符串或数组是否有效。index>=0 && index<=size *	IndexOutOfBoundsException			 */			Preconditions.checkPositionIndex(i, i);			/**			 * checkPositionIndexes(int start, int end, int size)	检查[start, end]表示的位置范围对某个列表、字符串或数组是否有效*	IndexOutOfBoundsException			 */			Preconditions.checkPositionIndexes(i, i, i);		}	}	@Test	public void testOptional() {		/**		 * Optional.of(T)	创建指定引用的Optional实例，若引用为null则快速失败		 */		String str = "abc";		Optional.of(str).toString();		/**		 * Optional.fromNullable(T)	创建指定引用的Optional实例，若引用为null则表示缺失		 * T or(T)	返回Optional所包含的引用，若引用缺失，返回指定的值		 */		String s = "def";		Optional.fromNullable(s).or(str).toString();	}	@Test	public void testObjects() {		/**		 * 简单编写toString方法		 */		System.out.println(Objects.toStringHelper(this).add("x", 1).toString());	}	@Test	public void testOrdering() {		/**		 * reverse()	获取语义相反的排序器		 * nullsFirst()	使用当前排序器，但额外把null值排到最前面。		 * nullsLast()	使用当前排序器，但额外把null值排到最后面。		 * compound(Comparator)	合成另一个比较器，以处理当前排序器中的相等情况。		 * lexicographical()	基于处理类型T的排序器，返回该类型的可迭代对象Iterable<T>的排序器。		 * onResultOf(Function)	对集合中元素调用Function，再按返回值用当前排序器排序。		 */		Ordering<Foo> ordering = Ordering.natural().reverse().onResultOf(new Function<Foo, Comparable>() {			public Comparable apply(Foo foo) {				return foo.sortedby;			}		});		Foo foo = new Foo(10);		for (Iterator iterator = foo.iterator(); iterator.hasNext();) {			System.out.println("sortedby : " + ((Foo)iterator.next()).getSortedby());		}		System.out.println("-------------------------------");		List<Foo> fooList = ordering.sortedCopy(foo);		for (Foo f : fooList) {			System.out.println("sortedby : " + f.getSortedby());		}	}	@Test	public void testImmutableCollection() {		ImmutableSortedSet.of("a", "b", "c", "a", "b", "d");	}	@Test	public void testLists() {		List<Person> personList= Lists.newArrayList(				new Person(1, "long", "123456"),				new Person(2, "id", "123456"),				new Person(3, "value", "123456"));		List<String> list = Lists.transform(personList, new Function<Person, String>() {			public String apply(Person p) {				return p.getUsername();			}		});		for (int i = 0; i < list.size(); i++) {			System.out.println(list.get(i));		}	}	@Test	public void testJoiner() {		System.out.println(Joiner.on("and").skipNulls().join("long", null, "fei"));		System.out.println(Joiner.on(",").join(Arrays.asList(1, 5, 7)));		System.out.println(Joiner.on("and").useForNull("long").join("shen", null, "fei"));	}	@Test	public void testSplitter() {		System.out.println(Splitter.on(",").trimResults().omitEmptyStrings().split("foo,bar,,  qux"));	}	@Test	public void testCharMatcher() {		System.out.println(CharMatcher.JAVA_ISO_CONTROL.removeFrom("java \n\r\t ios"));		System.out.println(CharMatcher.javaIsoControl().removeFrom("java \n\r\t ios"));		System.out.println(CharMatcher.DIGIT.retainFrom("a 1 b 2 c 3"));		System.out.println(CharMatcher.digit().retainFrom("a 1 b 2 c 3"));		System.out.println(CharMatcher.WHITESPACE.trimAndCollapseFrom(" a   b c ", ' '));		System.out.println(CharMatcher.whitespace().trimAndCollapseFrom(" a   b c ", ' '));		System.out.println(CharMatcher.JAVA_DIGIT.replaceFrom("a 1 b 2 c 3", "*"));		System.out.println(CharMatcher.javaDigit().replaceFrom("a 1 b 2 c 3", "*"));		System.out.println(CharMatcher.JAVA_DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom(" a b C 1 2 "));		System.out.println(CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom(" a b C 1 2 "));	}	@Test	public void testCaseFormat() {		System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME"));		System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, "CONSTANT_NAME"));		System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, "CONSTANT_NAME"));		System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "CONSTANT_NAME"));		System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE, "CONSTANT_NAME"));	}}class Foo implements Iterable<Foo> {	public Iterator<Foo> iterator() {		return new MyIterator();	}	class MyIterator implements Iterator<Foo> {		private int index = 0;		public boolean hasNext() {			return index != foo.length;		}		public Foo next() {			return foo[index++];		}		public void remove() {		}	}	String sortedby;	int notSortedBy;	public Foo(String sortedby, int notSortedBy) {		this.sortedby = sortedby;		this.notSortedBy = notSortedBy;	}	private Foo[] foo;	public Foo(int size) {		foo = new Foo[size];		for (int i = 0; i < size; i++) {			foo[i] = new Foo(UUID.randomUUID().toString(), i);		}	}	public String getSortedby() {		return sortedby;	}	public void setSortedby(String sortedby) {		this.sortedby = sortedby;	}	public int getNotSortedBy() {		return notSortedBy;	}	public void setNotSortedBy(int notSortedBy) {		this.notSortedBy = notSortedBy;	}}class Person {	private Integer id;	private String username;	private String password;	public Person(Integer id, String username, String password) {		this.id = id;		this.username = username;		this.password = password;	}	public Integer getId() {		return id;	}	public void setId(Integer id) {		this.id = id;	}	public String getUsername() {		return username;	}	public void setUsername(String username) {		this.username = username;	}	public String getPassword() {		return password;	}	public void setPassword(String password) {		this.password = password;	}}