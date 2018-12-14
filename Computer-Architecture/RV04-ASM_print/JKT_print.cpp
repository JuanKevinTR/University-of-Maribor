//-----------------------------------
// RVO4 - ASM_print (C++)
//-----------------------------------
// Subject: Computer Architecture
// Student: Juan Kevin Trujillo
//-----------------------------------
// WHAT TO DO IN THE TASK: Prints the student name, surname and email.


//-----------------------------------
// FOR COMPILING
// 		g++ JKT_print.cpp -o JKT_print.o && ./JKT_print.o
//-----------------------------------


//-----------------------------------
// FOR STRING
// 		#include <iostream>
//		using namespace std;
//-----------------------------------

#include <stdio.h>
#include <stdlib.h>

int main() {
	// Warning: Conversion from string literal to 'char *' is deprecated
	// Warning: ISO C++ forbids converting a string constant to ‘char*’
		char* name = "Juan Kevin";
		char* surname = "Trujillo";
		char* email = "juan.trujillo@student.um.si";
		char* format = "%s\n";

		printf(format, name);
		printf(format, surname);
		printf(format, email);

	// OTHER FORM TO PRINT (STRING)
		//string name = "Juan Kevin";
		//string surname = "Trujillo";
		//string email = "juan.trujillo@student.um.si";

		//cout << "\n" << name << "\n" << surname << "\n" << email << "\n" << endl;

	return 0;
}




