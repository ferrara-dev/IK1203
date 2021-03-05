defmodule Lst do

  def addFront([],element) do
    [element]
  end

  def addFront(lst, element) do
    [element | lst]
  end

  def addBack([], element) do
    [element]
  end

  def addBack(lst, element) do
    addBackP(lst, [element])
  end

  defp addBackP([],acc) do
    acc
  end

  defp addBackP([h | t],[accH | acc]) do
      addBackP(t, [h | acc])
  end

end
