;------------------------------------
; RV08 - ASM_recursion (Assembly)
;------------------------------------
; Subject: Computer Architecture
; Student: Juan Kevin Trujillo
;------------------------------------
; WHAT TO DO IN THE TASK: In an assembler language, write recursive function that calculates 
;						  the n-th term of a sequence in the series: 
;								f(n) = f(n-3) + f(n-2), n > 2, 
;								f(0) = 1, f(1)=1, f(2) = 7 
;						  In the main program, calculate the terms 3 to 15 and print the results.
;								f(3)	= f(0)	+ f(1)	= 1+1	= 2
;								f(4)	= f(1)	+ f(2)	= 1+7	= 8
;								f(5)	= f(2)	+ f(3)	= 7+2	= 9
;								f(6)	= f(3)	+ f(4)	= 2+8	= 10
;								f(7)	= f(4)	+ f(5)	= 8+9	= 17
;								f(8)	= f(5)	+ f(6)	= 9+10	= 19
;								f(9)	= f(6)	+ f(7)	= 10+17	= 27
;								f(10)	= f(7)	+ f(8)	= 17+19 = 36
;								f(11)	= f(8)	+ f(9)	= 19+27 = 46
;								f(12)	= f(9)	+ f(10)	= 27+36 = 63
;								f(13)	= f(10)	+ f(11)	= 36+46 = 82
;								f(14)	= f(11)	+ f(12)	= 46+63 = 109
;								f(15)	= f(12)	+ f(13)	= 63+82 = 145



;-----------------------------------
; FOR COMPILING (Ubuntu)
; 		nasm -f elf64 JKT_recursion.asm && gcc -no-pie JKT_recursion.o -m64 -o JKT_recursion && ./JKT_recursion
;-----------------------------------


;------------------------------------
; Declare needed C  functions
;------------------------------------
	extern	printf	; printf() C function

;------------------------------------
	SECTION .data			; Data section, initialized variables

		message_out: db "f(%d): %d", 10, 0

;------------------------------------
	SECTION .text			; Code section.

		global main			; the standard gcc entry point
		
			main:
				push rbp
				push rbx
				push r12

				mov rbp, 3			; rbp = 3
				mov rbx, 15			; rbx = 15
				
				;------------------------------------
			.FOR_LOOP:
				mov	rdi, rbp		; arg0 = i
				call Function		; funct(i), returns in rax

				mov rdi, message_out	; arg0 = message_out;
				mov rsi, rbp		; arg1 = i
				mov rdx, rax		; arg2 = f(i)
				mov	rax, 0			
				call printf			; printf(message_out, i, f(i))

				add rbp, 1			; i++
				cmp rbp, rbx	 	; i <= 15?
				jle .FOR_LOOP		; if yes --> continue loop
				
				; .FOR_LOOP is finished
				pop r12
    			pop rbx
				pop	rbp				; 	restore stack
				mov	rax, 0			; 	normal, no error, return value || ret_value := 0
				ret					; 	return

			Function:
				push rbp
				push rbx
				push r12

				mov rbp, rdi				; rbp = n

				mov rbx, 1			
				cmp rbp, rbx				; n > 1?
				jg .FunctionComparison		; if yes --> compare with 2
				mov rax, 1					; if not --> return 1 ==> f(0) = 1, f(1)=1
				jmp .FunctionEnded

			.FunctionComparison:
				mov rbx, 2		
				cmp rbp, rbx		; n == 2?
				jne .ComputeValue	; if not --> compute the value
				mov rax, 7			; if yes --> return 7 ==> f(2) = 7
				jmp .FunctionEnded

			.ComputeValue:
				mov rdi, rbp		; arg0 = n
				sub rdi, 3			; arg0 = n - 3
				call Function		; f(n-3), returns in rax
				mov r12, rax		; r12 = f(n-3)

				mov rdi, rbp		; arg0 = n
				sub rdi, 2			; arg0 = n - 2
				call Function		; f(n-2), returns in rax
				add rax, r12		; rax = f(n-3) + f(n-2) = return value

				;------------------------------------
			.FunctionEnded:
				pop r12
    			pop rbx
				pop	rbp				; 	restore stack
				ret					; 	return to FOR_LOOP














