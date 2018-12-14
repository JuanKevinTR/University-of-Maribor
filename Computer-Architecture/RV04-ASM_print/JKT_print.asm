;------------------------------------
; RV04 - ASM_print (Assembly)
;------------------------------------
; Subject: Computer Architecture
; Student: Juan Kevin Trujillo
;------------------------------------
; WHAT TO DO IN THE TASK: Prints the student name, surname and email.


;-----------------------------------
; FOR COMPILING (Ubuntu)
; 		nasm -f elf64 JKT_print.asm && gcc -no-pie JKT_print.o -m64 -o JKT_print && ./JKT_print
;-----------------------------------


;------------------------------------
; Equivalent C++ code using String
;------------------------------------
	; #include <iostream>
	; using namespace std;
	; int main(){
	;   string name = "Juan Kevin";
	;	string surname = "Trujillo";
	;	string email = "juan.trujillo@student.um.si";
	;   cout << name << "\n" << surname << "\n" << email << "\n" << endl;
	;   return 0;
	; }

;------------------------------------
; Declare needed C  functions
;------------------------------------
	extern	printf							; the C function, to be called

;------------------------------------
	SECTION .data			; Data section, initialized variables

name:		db "Juan Kevin", 0					; C string needs 0
surname:	db "Trujillo", 0					; 
email:		db "juan.trujillo@student.um.si", 0	; 
fmt:		db "%s", 10, 0          			; The printf format, "\n",'0'

;------------------------------------
	SECTION .text			; Code section.

        global main			; the standard gcc entry point
main:						; the program label for the entry point
        push    rbp			; set up stack frame, must be alligned

		;------------------------------------
		mov	rdi, fmt  		;	arg0 := fmt
		mov	rsi, name		;	arg1 := name
		mov	rax, 0			;	or can be  xor  rax,rax || vector_registers := 0
	    call printf			;	printf(fmt, name)

		;------------------------------------
	    mov	rdi, fmt  		; 	arg0 := fmt
		mov	rsi, surname	; 	arg1 := surname
		mov	rax, 0			; 	vector_registers := 0
	    call printf			; 	printf(fmt, surname)

	    ;------------------------------------
	    mov	rdi, fmt  		; 	arg0 := fmt
		mov	rsi, email		; 	arg1 := email
		mov	rax, 0			; 	vector_registers := 0
	    call printf			; 	printf(fmt, email)

		;------------------------------------
	    pop	rbp				; 	restore stack
		mov	rax, 0			; 	normal, no error, return value || ret_value := 0
		ret					; 	return




