;------------------------------------
; RV06 - ASM_div (Assembly)
;------------------------------------
; Subject: Computer Architecture
; Student: Juan Kevin Trujillo
;------------------------------------
; WHAT TO DO IN THE TASK: Lists all numbers between 1 and N that are divisible by 11.


;-----------------------------------
; FOR COMPILING (Ubuntu)
; 		nasm -f elf64 JKT_div.asm && gcc -no-pie JKT_div.o -m64 -o JKT_div && ./JKT_div
;-----------------------------------


;------------------------------------
; Declare needed C  functions
;------------------------------------
	extern	printf, scanf	; printf(), scanf() C function

;------------------------------------
	SECTION .bss

		N_number resd 1		; N number

;------------------------------------
	SECTION .data			; Data section, initialized variables

		fmt_string: db "%s", 0					; string print format, '0'
		fmt_string_newline: db "%s", 10, 0		; string print format, "\n", '0'

		message: db "Introduce int value for N: ", 0
		fmt_int_scanf: db "%d", 0		; print format (scanf)

		message_out: db "The numbers are: ", 0
		fmt_int_printf: db "%d ", 0		; print format (printf)

;------------------------------------
	SECTION .text			; Code section.

		global main			; the standard gcc entry point

			main:
				push rbp		; set up stack frame, must be alligned
				mov r12, 11     ; r12 = 11 (divider)
				
				;------------------------------------
				mov rdi, fmt_string 	; arg0 = fmt_string (destination_index)
				mov rsi, message		; source_index = arg1 = message
				mov rax, 0				; rax = 0
				call printf				; printf(fmt_string, message)

				;------------------------------------
				mov rdi, fmt_int_scanf	; arg0 = fmt_int_scanf
				mov rsi, N_number		; arg1 = &n (the value of n)
				mov rax, 0				; rax = 0
				call scanf				; scanf(fmt_int_scanf, n)

				;------------------------------------
				mov rbx, [N_number]		; rbx = n
				mov rbp, 1				; rbp = i = 1
				cmp rbx, rbp			; IF n < i ?
				jl .FINISH					; if true -> finish program

				;------------------------------------
				mov rdi, fmt_string		; arg0 = fmt_string
				mov rsi, message_out	; arg1 = message_out
				mov rax, 0				; rax = 0
				call printf				; printf(fmt_string, message_out)

				;------------------------------------
			.FOR_LOOP:

				mov edx, 0				; edx = 0, remainder
				mov rax, rbp			; rax = i, dividend
				div r12					; div (i, 12), where result = eax and remainder = edx

				cmp edx, 0				; IF remainder not equal to 0
				jne .FOR_CONTINUE			; jump to continue
											; else print number

				mov rdi, fmt_int_printf	; arg0 = fmt_int_printf
				mov rsi, rbp			; arg1 = i
				mov rax, 0				; rax = 0
				call printf				; printf(fmt_int_printf, i)

				;------------------------------------
			.FOR_CONTINUE:
				add rbp, 1				; i++;
				cmp rbp, rbx			; IF i <= n ?
				jle .FOR_LOOP				; if true -> continue the loop


				; NEW LINE---------------------------
				mov rdi, fmt_string_newline		; arg0 = fmt_string
				mov rax, 0						; rax = 0
				call printf						; printf(fmt_string)					

				;------------------------------------
			.FINISH:	
				pop	rbp				; 	restore stack
				mov	rax, 0			; 	normal, no error, return value || ret_value := 0
				ret					; 	return






