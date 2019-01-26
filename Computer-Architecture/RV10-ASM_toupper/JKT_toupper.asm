;------------------------------------
; RV10 - ASM_toupper (Assembly)
;------------------------------------
; Subject: Computer Architecture
; Student: Juan Kevin Trujillo
;------------------------------------												 -
; WHAT TO DO IN THE TASK: In assembly language, write function "upper" that converts a string to 
;							uppercase. Use the dedicated instructions and registers:
;								(esi, edi; lodsb, stosb, cld, std)
;							To read and write call interrupts using instruction int (e.g. int 10h). 
;							In the main program show usage of the functions.


;-----------------------------------
; FOR COMPILING (Ubuntu)
; 		nasm -f elf64 JKT_toupper.asm && gcc -no-pie JKT_toupper.o -m64 -o JKT_toupper && ./JKT_toupper
;-----------------------------------


;------------------------------------
; Declare needed C  functions
;------------------------------------
	extern	printf	; printf() C function

;------------------------------------
	SECTION .bss

		string:		resb 100		; chart string[100]

;------------------------------------
	SECTION .data			; Data section, initialized variables

		user_string:	db "Write a lowercase string: ", 10, 0

;------------------------------------
	SECTION .text			; Code section.

		global main			; the standard gcc entry point
		
			main:
				push rbp
				push r12
				push r13
				push r14
				push r15

				;------------------------------------
				mov rdi, user_string 	;	arg0 := user_string;
				mov	rax, 0				;	vector_registers := 0
				call printf				;	printf(user_string) 

				;------------------------------------
				mov rax, 3			;	service_num = 3 (the system interprets 3 as "read")
				mov rcx, string		;	rcx = @string (address to pass to)
				mov rdx, 100		;	max_chars = 100
				mov rbx, 0			;	rbx = stdin (read from standard input)
				int 80h				;	call the kernel = read(@string, max_chars)

				mov rbp, string		;	rbp = @string
				mov r12B, [rbp]		;	r12B = string[0] - First position, first char
				mov r13B, 0			;	r13B = '\0'
				mov r14B, 97		;	r14B = 'a'  in decimal ASCII
				mov r15B, 122		;	r15B = 'z'  in decimal ASCII

				;------------------------------------
			.WHILE_LOOP:
				cmp r12B, r14B		;	string[x] < 'a' ? 
				jb .CONTINUE		;	if yes --> next char (below)<
				cmp r12B, r15B		;	string[x] > 'z' ? 
				ja  .CONTINUE		;	if yes --> next char (above)>

				sub r12B, 32		; 	value of UPPER letter ASCII
				mov [rbp], r12B		;	string[x] = uppercase

				;------------------------------------
			.CONTINUE:
				add rbp, 1
				mov r12B, [rbp]		;	r12B = string[x+1]

				cmp r12B, r13B		;	string[x+1] == '\0' ? 
				jne .WHILE_LOOP

				mov rax, 4			;	service_num = 4 (the system interprets 4 as "write")
				mov rcx, string		;	rcx = @string (pointer to the value being passed)
				mov rdx, 100		;	max_chars = 100
				mov rbx, 1			;	rbx = stout (standard output (print to terminal))
				int 80h				;	call the kernel = write(@string, max_chars)

				;------------------------------------
			.FINISH:	
				pop r15
				pop r14
				pop r13
				pop r12
				pop rbp

				mov rax, 0
				ret






