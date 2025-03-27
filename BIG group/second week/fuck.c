#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 最初的想法，数字和运算符分开存放，但是输入的是字符串，操作有点麻烦
typedef struct
{
    int Num;
    char Operator;
} Structure;

typedef struct Reckoner
{
    char data;
    struct Reckoner *next;
} Reckoner, *top; // 名字叫计算器，定义的栈顶指针为top

void InitStack(top *S)
{
    *S = NULL; // 初始化为空栈
}

// 判断栈是否为空
int StackEmpty(top S)
{
    return S == NULL; // 如果栈顶指针为空，则栈为空
}

// 入栈
void Push(top *S, char e)
{
    Reckoner *newNode = (Reckoner *)malloc(sizeof(Reckoner));
    newNode->data = e;
    newNode->next = *S; // 新节点指向当前栈顶
    *S = newNode;       // 更新栈顶指针的位置
}

// 出栈
char Pop(top *S)
{
    if (StackEmpty(*S))
    {
        printf("Stack underflow\n");
        exit(1);
    }
    Reckoner *temp = *S;
    char e = temp->data;
    *S = temp->next; // 更新栈顶指针
    free(temp);      // 释放内存
    return e;
}

// 拿取栈顶的数字或运算符
char GetTop(top S)
{
    if (StackEmpty(S))
    {
        printf("Stack is empty\n");
        exit(1);
    }
    return S->data;
}

// 判断运算优先级
int Priority(char op)
{
    switch (op)
    {
    case '+':
        return 1;
    case '-':
        return 1;
    case '*':
        return 2;
    case '/':
        return 2;
    default:
        return 0;
    }
}

// 定义四个运算
int Calculate(char a, char b, char op)
{
    int num1 = a - '0'; // 将字符转换为整数
    int num2 = b - '0';
    switch (op)
    {
    case '+':
        return num1 + num2 + '0'; // 将结果转换回字符
    case '-':
        return num1 - num2 + '0';
    case '*':
        return num1 * num2 + '0';
    case '/':
        return num1 / num2 + '0';
    }
    return 0;
}

// 中缀转后缀，便于计算机计算
void InfixToPostfix(char *infix, char *postfix)
{
    top S;
    InitStack(&S);

    int i = 0, j = 0;
    while (infix[i] != '\0')
    {
        char c = infix[i];
        if (c >= '0' && c <= '9')
        {                     // 如果是数字
            postfix[j++] = c; // 直接输出
        }
        else if (c == '(')
        {                // 如果是左括号
            Push(&S, c); // 入栈
        }
        else if (c == ')')
        { // 如果是右括号
            if (StackEmpty(S))
            {
                printf("Error: Mismatched parentheses\n");
                exit(1);
            }
            while (!StackEmpty(S) && GetTop(S) != '(')
            {
                postfix[j++] = Pop(&S); // 弹出操作符到后缀表达式
            }
            if (!StackEmpty(S) && GetTop(S) == '(')
            {
                Pop(&S); // 弹出左括号
            }
            else
            {
                printf("Error: Mismatched parentheses\n");
                exit(1);
            }
        }
        else
        { // 如果是操作符
            while (!StackEmpty(S) && Priority(GetTop(S)) >= Priority(c))
            {
                postfix[j++] = Pop(&S); // 弹出操作符到后缀表达式
            }
            Push(&S, c); // 将当前操作符入栈
        }
        i++;
    }

    // 处理栈中剩余的操作符
    while (!StackEmpty(S))
    {
        if (GetTop(S) == '(')
        {
            printf("Error: Mismatched parentheses\n");
            exit(1);
        }
        postfix[j++] = Pop(&S);
    }
    postfix[j] = '\0'; // 结束后缀表达式
}
// 计算
int EvaluatePostfix(char *postfix)
{
    top S;
    InitStack(&S);

    int i = 0;
    while (postfix[i] != '\0')
    {
        char c = postfix[i];
        if (c >= '0' && c <= '9')
        {                // 如果是操作数
            Push(&S, c); // 入栈
        }
        else
        { // 如果是操作符
            if (StackEmpty(S) || StackEmpty(S->next))
            {
                printf("Error: Invalid postfix expression\n");
                exit(1);
            }
            int b = Pop(&S) - '0';           // 弹出操作数
            int a = Pop(&S) - '0';           // 弹出操作数
            int result = Calculate(a, b, c); // 计算结果
            Push(&S, result + '0');          // 将结果压入栈
        }
        i++;
    }

    if (StackEmpty(S))
    {
        printf("Error: Invalid postfix expression\n");
        exit(1);
    }

    return Pop(&S) - '0'; // 返回最终结果
}

int main()
{
    char infix[100];
    char postfix[100]; // 存储后缀表达式
    printf("请输入计算式：");
    fgets(infix, sizeof(infix), stdin);
    infix[strcspn(infix, "\n")] = '\0'; // 去掉换行符
    printf("%s\n", infix);

    InfixToPostfix(infix, postfix); // 中缀转后缀
    printf("Postfix expression: %s\n", postfix);

    int result = EvaluatePostfix(postfix); // 计算后缀表达式
    printf("The result is: %d\n", result);

    return 0;
}