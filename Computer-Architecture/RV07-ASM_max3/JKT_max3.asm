;------------------------------------
; RV07 - ASM_max3 (Assembly)
;------------------------------------
; Subject: Computer Architecture
; Student: Juan Kevin Trujillo
;------------------------------------
; WHAT TO DO IN THE TASK: Write in assembly language a function max3, receiving three 
;						  integers and returning the maximum between them. The main 
;						  program displays usage of the function.


;-----------------------------------
; FOR COMPILING (Ubuntu)
; 		nasm -f elf64 JKT_max3.asm && gcc -no-pie JKT_max3.o -m64 -o JKT_max3 && ./JKT_max3
;		./JKT_max3
;-----------------------------------


;------------------------------------
; Declare needed C  functions
;------------------------------------
	extern	printf, atoi	; printf(), atoi() C function

;------------------------------------
	SECTION .data			; Data section, initialized variables

		usage: db "Error!! Use this format ./JKT_max3 Int1 Int2 Int3", 10, 0
		message_out: db "The maximum number is: %d", 10, 0

;------------------------------------
	SECTION .text			; Code section.

		global main			; the standard gcc entry point
		
			main:
				push rbp	; set up stack frame, must be alligned
				push rbx
				push r12	; Max_Integer

				;------------------------------------
				mov rbx, rdi		; rbx = argc
				mov rbp, rsi		; rpb = @argv

				cmp rbx, 4			; argc <=> 3 ?
				je .firstCheck

				;------------------------------------
				mov rdi, usage 		; arg0 = usage
				mov rax, 0
				call printf			; printf(usage)
				mov rax, -1
				jmp .FINISH

				;------------------------------------
			.firstCheck:
				mov rdi, [rbp+8]	; arg0 = argv[1] = Int1
				call atoi			; atoi(argv[1]) returns in rax (String to Integer)
				mov r12, rax		; r12 = Max_Integer = Int1

				mov rdi, [rbp+16]	; arg0 = argv[2] = Int2
				call atoi			; atoi(argv[2]) returns in rax (String to Integer)
				cmp r12, rax		; r12 >= Int2 ? 
				jge .secondCheck	; if yes --> continue
				mov r12, rax		; if not --> r12 = Max_Integer = Int2

				;------------------------------------
			.secondCheck:
				mov rdi, [rbp+24]   ; arg0 = argv[3] = Int3
				call atoi           ; atoi(argv[3]) returns in rax (String to Integer)
				cmp r12, rax        ; r12 >= Int3 ? 
				jge .printMax		; if yes --> continue
				mov r12, rax        ; if not --> r12 = Max_Integer = Int3

				;------------------------------------
			.printMax:
				mov rdi, message_out	; arg0 = message_out
				mov rsi, r12			; arg = r12 = Max_Integer 
				mov rax, 0
				call printf
				mov rax, 0

				;------------------------------------
			.FINISH:
				pop r12
    			pop rbx
				pop	rbp				; 	restore stack
				mov	rax, 0			; 	normal, no error, return value || ret_value := 0
				ret					; 	return











